package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.dto.PostDto;
import com.homesharing_backend.data.dto.PostTopRateDto;
import com.homesharing_backend.data.dto.SearchDto;
import com.homesharing_backend.data.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM Post order by id DESC LIMIT 4", nativeQuery = true)
    List<Post> getPostTop();

    Post getPostById(Long id);

    Boolean existsPostById(Long id);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.PostTopRateDto(p.id, pi.imageUrl, avg(r.point) , p.title) FROM Post p" +
            " left join PostImage pi on p.id = pi.post.id left join BookingDetail bd" +
            " on p.id = bd.post.id left join Rate r on r.bookingDetail.id=bd.id " +
            " group by p.id order by avg(r.point) desc ")
    List<PostTopRateDto> getTopPostByRate();

    @Query(value = "SELECT new com.homesharing_backend.data.dto.PostTopRateDto(p.id, pi.imageUrl, avg(r.point) , p.title) FROM Post p" +
            " left join PostImage pi on p.id = pi.post.id left join BookingDetail bd" +
            " on p.id = bd.post.id left join Rate r on r.bookingDetail.id=bd.id WHERE p.id =:postID " +
            " group by p.id order by avg(r.point) desc")
    Optional<PostTopRateDto> getPostDetailByPostID(@Param("postID") Long postID);

    List<Post> getPostByHost_Id(Long hostID);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.PostDto(p.id, p.title, pi.imageUrl, rp.id, rp.status," +
            "p.status, pm.status, pm.endDate, pm.startDate, avg(r.point)) FROM Post p " +
            "left join PostImage pi on p.id = pi.post.id " +
            "left join PostPayment  pm on p.id = pm.post.id " +
            "left join BookingDetail bd on p.id = bd.post.id " +
            "left join ReportPost rp on p.id = rp.post.id " +
            "left join Rate r on r.bookingDetail.id=bd.id WHERE p.host.id =:hostID " +
            "GROUP BY p.id order by avg(r.point) desc")
    public List<PostDto> getPostDTO(@Param("hostID") Long hostID);

    @Query("SELECT new com.homesharing_backend.data.dto.SearchDto(p.id, p.title, pd.address, p.price," +
            " pi.imageUrl, v.code, avg(r.point)) FROM Post p " +
            "LEFT JOIN PostDetail pd on p.id = pd.post.id " +
            "LEFT join PostVoucher pv on p.id = pv.post.id " +
            "LEFT JOIN Voucher v on pv.voucher.id = v.id " +
            "LEFT JOIN PostImage pi on p.id = pi.post.id " +
            "LEFT JOIN BookingDetail bd on p.id = bd.post.id " +
            "LEFT JOIN Rate r on bd.id = r.bookingDetail.id WHERE p.title LIKE %:title% OR pd.address LIKE %:title% " +
            "GROUP BY p.id")
    List<SearchDto> listSearchPostByTitle(String title);

    @Query("SELECT new com.homesharing_backend.data.dto.SearchDto(p.id, p.title, pd.address, p.price," +
            " pi.imageUrl, v.code, avg(r.point)) FROM Post p " +
            "LEFT JOIN PostDetail pd on p.id = pd.post.id " +
            "LEFT join PostVoucher pv on p.id = pv.post.id " +
            "LEFT JOIN Voucher v on pv.voucher.id = v.id " +
            "LEFT JOIN PostImage pi on p.id = pi.post.id " +
            "LEFT JOIN BookingDetail bd on p.id = bd.post.id " +
            "LEFT JOIN Rate r on bd.id = r.bookingDetail.id WHERE pd.address LIKE %:provinceName% " +
            "GROUP BY p.id")
    List<SearchDto> listSearchPostByProvince(String provinceName);
}
