package com.homesharing_backend.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "history_handle_report_post_detail")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class HistoryHandleReportPostDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "history_handle_report_post_id", referencedColumnName = "id")
    private HistoryHandleReportPost historyHandleReportPost;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_post_id", referencedColumnName = "id")
    private ReportPost reportPost;

    @Column(name = "status_history")
    private int statusHistory;

}
