package com.das.batchrestore.repository;

import com.das.batchrestore.model.RestoreModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class JdbcRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    //리스토어 요청 리스트 추출
    public List<RestoreModel> selectRestoreList(String pgmCd) {
        String Sql = "select mst.title, mst.epis_no, inst.out_system_id from metadat_mst_tbl mst "
                + "inner join contents_inst_tbl inst on mst.rpimg_ct_id = inst.ct_id "
                + "where mst.pds_cms_pgm_id =? and mst.ctgr_l_cd = '200' and inst.dtl_yn ='Y' and value(mst.del_dd,'') = '' "
                + "and(mst.epis_no > 0 and mst.epis_no < 157) and mst.title not like '%오류%' and mst.title like '%OTT%' "
                + "order by epis_no desc";
        List<RestoreModel> restoreList = jdbcTemplate.query(Sql, (rs, rowNum) ->
                new RestoreModel(rs.getString("title"), rs.getString("epis_no"), rs.getString("out_system_id")),pgmCd);
        return restoreList;
    }

}