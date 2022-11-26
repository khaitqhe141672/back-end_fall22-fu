package com.homesharing_backend.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "history_handle_report_post")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class HistoryHandleReportPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "complaint_post_id", referencedColumnName = "id")
    private ComplaintPost complaintPost;

    @Column(name = "status_report")
    private int statusReport;

    @Column(name = "status_post")
    private int statusPost;

}
