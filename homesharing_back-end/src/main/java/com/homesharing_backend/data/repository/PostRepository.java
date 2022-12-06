package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.dto.*;
import com.homesharing_backend.data.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM Post p WHERE p.status = 1 AND ((p.status_report IS NULL) OR (p.status_report = 1)) order by p.id DESC LIMIT 4", nativeQuery = true)
    List<Post> getPostTop();

    Post getPostById(Long id);

    Boolean existsPostById(Long id);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.PostTopRateDto(p.id, pi.imageUrl, avg(r.point) , p.title) FROM Post p" +
            " left join PostImage pi on p.id = pi.post.id left join BookingDetail bd" +
            " on p.id = bd.post.id left join Rate r on r.bookingDetail.id=bd.id WHERE p.status = 1 AND ((p.statusReport IS NULL) OR (p.statusReport = 1)) " +
            "group by p.id order by avg(r.point) desc ")
    Page<PostTopRateDto> getTopPostByRate(PageRequest pageRequest);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.PostTopRateDto(p.id, pi.imageUrl, avg(r.point) , p.title) FROM Post p" +
            " left join PostImage pi on p.id = pi.post.id left join BookingDetail bd" +
            " on p.id = bd.post.id left join Rate r on r.bookingDetail.id=bd.id WHERE p.id =:postID AND ((p.statusReport IS NULL) OR (p.statusReport = 1))" +
            " group by p.id order by avg(r.point) desc")
    Optional<PostTopRateDto> getPostDetailByPostID(@Param("postID") Long postID);

    List<Post> getPostByHost_Id(Long hostID);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.PostDto(p.id, p.title, pi.imageUrl, rp.id, rp.status," +
            "p.status, pm.status, pm.endDate, pm.startDate, avg(r.point)) FROM Post p " +
            "left join PostImage pi on p.id = pi.post.id " +
            "left join PostPayment  pm on p.id = pm.post.id " +
            "left join BookingDetail bd on p.id = bd.post.id " +
            "left join ReportPost rp on p.id = rp.post.id " +
            "left join Rate r on r.bookingDetail.id=bd.id WHERE p.host.id =:hostID AND ((p.statusReport IS NULL) OR (p.statusReport = 1))" +
            "GROUP BY p.id order by avg(r.point) desc")
    public List<PostDto> getPostDTO(@Param("hostID") Long hostID);

    @Query("SELECT new com.homesharing_backend.data.dto.SearchDto(p.id, p.title, pd.address, p.price," +
            " pi.imageUrl, v.description, avg(r.point), p.host.typeAccount) FROM Post p " +
            "LEFT JOIN PostDetail pd on p.id = pd.post.id " +
            "LEFT join PostVoucher pv on p.id = pv.post.id " +
            "LEFT JOIN Voucher v on pv.voucher.id = v.id " +
            "LEFT JOIN PostImage pi on p.id = pi.post.id " +
            "LEFT JOIN BookingDetail bd on p.id = bd.post.id " +
            "LEFT JOIN Rate r on bd.id = r.bookingDetail.id WHERE p.title LIKE %:title% " +
            "AND ((p.statusReport IS NULL) OR (p.statusReport = 1))" +
            "GROUP BY p.id")
    Page<SearchDto> listSearchPostByTitle(@Param("title") String title, PageRequest pageRequest);

    @Query("SELECT new com.homesharing_backend.data.dto.SearchDto(p.id, p.title, pd.address, p.price," +
            " pi.imageUrl, v.code, avg(r.point), p.host.typeAccount) FROM Post p " +
            "LEFT JOIN PostDetail pd on p.id = pd.post.id " +
            "LEFT join PostVoucher pv on p.id = pv.post.id " +
            "LEFT JOIN Voucher v on pv.voucher.id = v.id " +
            "LEFT JOIN PostImage pi on p.id = pi.post.id " +
            "LEFT JOIN BookingDetail bd on p.id = bd.post.id " +
            "LEFT JOIN Rate r on bd.id = r.bookingDetail.id WHERE pd.address LIKE %:provinceName% " +
            "GROUP BY p.id")
    List<SearchDto> listSearchPostByProvince(@Param("provinceName") String provinceName);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.PostDto(p.id, p.title, p.status, avg(r.point)) from Post p " +
            "LEFT JOIN PostDetail pd ON p.id = pd.post.id " +
            "LEFT JOIN BookingDetail bd ON p.id = bd.post.id " +
            "LEFT JOIN Rate r ON bd.id = r.bookingDetail.id WHERE p.host.id= :hostID " +
            "GROUP BY p.id ")
    Page<PostDto> listPostByHost(@Param("hostID") Long hostID, PageRequest pageRequest);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.ViewBookingDto(b.id, bd.id, p.id, p.title, bd.startDate, " +
            "bd.endDate, b.totalMoney, bd.totalPerson, bd.totalService, b.note, b.status) FROM Booking b\n" +
            "left join BookingDetail bd on b.id = bd.booking.id\n" +
            "left join Post p on bd.post.id = p.id where b.status = :status and p.host.id= :hostID \n" +
            "group by b.id\n")
    Page<ViewBookingDto> getAllStatusBooking(@Param("status") int status, @Param("hostID") Long hostID, PageRequest pageRequest);

    Page<Post> getPostByHost_Id(Long hostID, PageRequest pageRequest);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.ViewBookingDto(b.id, bd.id, p.id, p.title, bd.startDate, " +
            "bd.endDate, b.totalMoney, bd.totalPerson, bd.totalService, b.note, b.status) FROM Booking b " +
            "LEFT JOIN BookingDetail bd on b.id = bd.booking.id " +
            "LEFT JOIN Post p on bd.post.id = p.id WHERE p.host.id= :hostID AND b.status= 2 AND (current_date() between bd.startDate AND bd.endDate)")
    Page<ViewBookingDto> getAllCurrentBooking(@Param("hostID") Long hostID, PageRequest pageRequest);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.SearchDto(p.id, p.title, pd.address, p.price, pi.imageUrl, v.description, " +
            "avg(r.point), h.typeAccount, h.user.userDetail.fullName, pr.id, pd.guestNumber, pd.roomType.id) FROM Post p " +
            "LEFT JOIN PostDetail pd ON p.id = pd.post.id " +
            "LEFT JOIN District d ON pd.district.id = d.id " +
            "LEFT JOIN Province pr ON d.province.id = pr.id " +
            "LEFT JOIN PostImage pi ON p.id = pi.post.id " +
            "LEFT JOIN PostServices ps ON p.id = ps.post.id " +
            "LEFT JOIN PostVoucher pv ON p.id = pv.post.id " +
            "LEFT JOIN Voucher v ON pv.voucher.id = v.id " +
            "LEFT JOIN Host h ON p.host.id = h.id " +
            "LEFT JOIN User u ON h.user.id = u.id " +
            "LEFT JOIN UserDetail ud ON u.userDetail.userDetailId = ud.userDetailId " +
            "LEFT JOIN BookingDetail bd ON p.id = bd.post.id " +
            "LEFT JOIN Rate r ON bd.id = r.bookingDetail.id WHERE p.status = 1 AND ((p.statusReport IS NULL) OR (p.statusReport = 1)) " +
            "GROUP BY p.id")
    List<SearchDto> getSearchFilter();

    @Query(value = "SELECT new com.homesharing_backend.data.dto.FillSearchDto(p.id, p.title, pi.imageUrl, p.price, pv.name) FROM Post p " +
            "LEFT JOIN PostDetail pd ON p.id = pd.post.id " +
            "LEFT JOIN PostImage pi ON p.id = pi.post.id " +
            "LEFT JOIN District d ON pd.district.id = d.id " +
            "LEFT JOIN Province pv ON d.province.id = pv.id " +
            "where p.title like %:title% and p.status = 1 " +
            "GROUP BY p.id")
    List<FillSearchDto> searchPostByTitle(@Param("title") String title, PageRequest pageRequest);

    @Query(value = "SELECT distinct p.id FROM Post p " +
            "left join BookingDetail bd on p.id = bd.post.id " +
            "left join Booking b on bd.booking.id = b.id where b.status = 2 " +
            "and bd.startDate = :date and p.status = 1 " +
            "AND ((p.statusReport IS NULL) OR (p.statusReport = 1)) " +
            "group by p.id")
    List<Long> getAllSearchByDate(@Param("date") Date date);

    @Query(value = "SELECT count(*) FROM post WHERE MONTH(create_date)= :createDate", nativeQuery = true)
    int getAllMonth(@Param("createDate") int createDate);

    @Query(value = "SELECT count(*) FROM post p where p.status= :status", nativeQuery = true)
    int totalPostActive(@Param("status") int status);

    @Query(value = "SELECT count(*) FROM post p where p.status!= 1", nativeQuery = true)
    int totalPostDeActiveByAdmin();

    @Query(value = "SELECT count(*) FROM post ", nativeQuery = true)
    int totalPost();


    int countPostByHost_IdAndStatus(Long hostID, int status);

    int countPostByHost_Id(Long hostID);

    @Query(value = "SELECT count(*) FROM demo.post where host_id= :hostID AND status!= 2;", nativeQuery = true)
    int totalPostDeActive(@Param("hostID") Long hostID);
}
