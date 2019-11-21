package zero.dao.impl;


import zero.tool.ConnectionUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description:
 * @Author: DarkFuneral
 * @CreateDate: 2018/6/7.08:21
 * @Project: KJ_EPO_WS
 */
public class testDao {

    private final String sql_update = "update pmm_file set pmmud13 = to_date(?,'yyyy/MM/dd') where pmm01 = ?";
    private final String sql_update1 = "update oha_file set ohaud03 = '1' where oha01 = ?";
    private final String sql_pmm = "update pmm_file set pmm21 = ?,pmm43 = ? where pmm01 = ?";
    private final String sql_pmn = "update pmn_file set pmn31t = ? where pmn01 = ? and pmn02 = ?";
    private final String sql_rva = "update rva_file set rva116 = ? where rva01 = ?";
    private final String sql_rvb = "update rvb_file set rvb10t = ?,rvb88t = ? where rvb01 = ? and rvb02 = ?";
    private final String sql_rvu = "update rvu_file set rvu12 = ? where rvu01 = ?";
    private final String sql_rvv = "update rvv_file set rvv38t = ?,rvv39t = ? where rvv01 = ? and rvv02 = ?";
    private final String sql_up = "update pmn_file set pmn88t = pmn20 * pmn31t where pmn01 = ? and pmn02=?";
    private final String sql_updatePmm40 = "update pmm_file set pmm40t = (select sum(pmn88t) from pmn_file where pmn01 = ?) where pmm01 = ?";

    private final String sql_delpml1 = "update pml_file set pml16 = '6' where pml01 = ? and pml20 = pml21";
    private final String sql_delpml2 = "update pml_file set pml16 = '7' where pml01 = ? and pml20 < pml21";
    private final String sql_delpml3 = "update pml_file set pml16 = '8' where pml01 = ? and pml20 > pml21";
    private final String sql_delpmk = "update pmk_file set pmk25 = '6' where pmk01 = ?";

    private final String sql_upgem = "update gem_file set gem02 = ?,gem03 = ? where gem01 = ?";
    private final String sql_delgem = "update gem_file set gemacti = 'N' where gem01 = ?";
    private final String sql_addgem = "insert into gem_file(gem01,gem02,gem03,gem04,gem05,gem06,gemacti,gemuser,gem09,gemorig,gemoriu,ta_gem01,ta_gem04) " +
            "values(?,?,?,?,? ,?,?,?,?,? ,?,?,?) ";


    private final String sql_upfak = "update fak_file set fak04=?,fak13=?,fak14=?,fak19=?,fak20=?,fak21=?,ta_fak11=?,fak24=?,fak33=?,fak62=?,fak68=? where fak02 = ?";
    private final String sql_upfaj = "update faj_file set faj43=?,faj25=to_date(?,'yyyy/MM/dd'),faj26=to_date(?,'yyyy/MM/dd'),faj27=?," +
            "faj34=?,faj28=?,faj29=?,faj30=?,faj71=?,faj61=?,faj64=?,faj65=?, faj31=?,faj66=? where faj02=?";  //faj04=?,faj13=?,faj14=?,faj19=?,faj20=?,faj21=?,ta_faj11=?," +
//            "faj24=?,faj33=?,faj62=?,faj68=?,ta_faj32=?,faj31=?,faj66=? where faj02=?";

    private final String sql = "update faj_file set faj201=?,faj13=?,faj33=?,faj62=?,faj68=? where faj02 = ?";
    private final String up_pmn = "update kaijia.FAJ_file set faj20 = ?,faj24=? where FAJ02 = ?";
//    private final String update_zy = "update kaijia.pmk_file set pmk25 = '6' where pmk01 = ?";


    protected Connection conn = null;
    protected PreparedStatement ps = null;
    protected ResultSet resultSet = null;

    /***
     * 组织更新
     *
     * @param str
     */
    public void updateOrg(String[] str) {
        try {
            conn = ConnectionUtil.getConnection();
            ps = (PreparedStatement) conn.prepareStatement(sql_upgem);
            ps.setString(1, str[1]);
            ps.setString(2, str[1]);
            ps.setString(3, str[0]);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(ps, resultSet, conn);
        }
    }

    /***
     * 组织失效
     *
     * @param str
     */
    public void deleteOrg(String[] str) {
        try {
            conn = ConnectionUtil.getConnection();
            ps = (PreparedStatement) conn.prepareStatement(sql_delgem);
            ps.setString(1, str[0]);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(ps, resultSet, conn);
        }
    }

    /**
     * 组织增加
     *
     * @param str
     */
    public void addOrg(String[] str) {
        try {
            conn = ConnectionUtil.getConnection();
            ps = (PreparedStatement) conn.prepareStatement(sql_addgem);
            ps.setString(1, str[0]);
            ps.setString(2, str[1]);
            ps.setString(3, str[1]);
            ps.setString(4, "鎧嘉");
            ps.setString(5, "Y");
            ps.setString(6, "KAIJIA");
            ps.setString(7, "Y");
            ps.setString(8, "TIPTOP");
            ps.setString(9, "3");
            ps.setString(10, "JGBA01");
            ps.setString(11, "TIPTOP");
            ps.setString(12, "N");
            ps.setString(13, "Y");

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(ps, resultSet, conn);
        }
    }

    /**
     * 请购结案
     *
     * @param s
     */
    public void updatePmk(String s) {

        try {

            conn = ConnectionUtil.getConnection();
            //将事务模式设置为手动提交事务
            conn.setAutoCommit(false);
            //设置事务的隔离级别
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            ps = (PreparedStatement) conn.prepareStatement(sql_delpmk);
            ps.setString(1, s);
            ps.executeUpdate();

            ConnectionUtil.releasePreparedStatement(ps);
            ps = (PreparedStatement) conn.prepareStatement(sql_delpml1);
            ps.setString(1, s);
            ps.executeUpdate();

            ConnectionUtil.releasePreparedStatement(ps);
            ps = (PreparedStatement) conn.prepareStatement(sql_delpml2);
            ps.setString(1, s);
            ps.executeUpdate();

            ConnectionUtil.releasePreparedStatement(ps);
            ps = (PreparedStatement) conn.prepareStatement(sql_delpml3);
            ps.setString(1, s);
            ps.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(ps, resultSet, conn);
        }
    }

    /**
     * 更新请购采购日期
     *
     * @param no
     */
    public void updateData(String no) {

        try {
            conn = ConnectionUtil.getConnection();
            //将事务模式设置为手动提交事务
            conn.setAutoCommit(false);
            //设置事务的隔离级别
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            ps = (PreparedStatement) conn.prepareStatement(sql_update1);
            ps.setString(1, no);
            ps.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            System.out.println(e);
        } finally {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            release(ps, resultSet, conn);
        }
    }

    /**
     * 17 -> 16 税率更改
     *
     * @param str
     */
    public void updateTax(String[] str) {

        String rvu01 = str[0];
        String rvv02 = str[1].substring(0, str[1].indexOf("."));
        String pmm01 = str[2];
        String pmn02 = str[3].substring(0, str[3].indexOf("."));
        String rva01 = str[4];
        String rvb02 = str[5].substring(0, str[5].indexOf("."));
        BigDecimal price = new BigDecimal(str[6]);
        BigDecimal priceTax = new BigDecimal(str[7]);
        String type = str[8].substring(0, 4);
//        BigDecimal priceSum = new BigDecimal(str[9]);
        Long tax = 13L;

        try {
            conn = ConnectionUtil.getConnection();
            //将事务模式设置为手动提交事务
            conn.setAutoCommit(false);
            //设置事务的隔离级别
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            ps = (PreparedStatement) conn.prepareStatement(sql_pmm);
            ps.setString(1, type);
            ps.setLong(2, tax);
//            ps.setBigDecimal(3,priceSum);
            ps.setString(3, pmm01);
            ps.executeUpdate();

            ConnectionUtil.releasePreparedStatement(ps);
            ps = (PreparedStatement) conn.prepareStatement(sql_pmn);
            ps.setBigDecimal(1, price);
//            ps.setBigDecimal(2,priceTax);
            ps.setString(2, pmm01);
            ps.setString(3, pmn02);
            ps.executeUpdate();

            ConnectionUtil.releasePreparedStatement(ps);
            ps = (PreparedStatement) conn.prepareStatement(sql_rva);
            ps.setLong(1, tax);
            ps.setString(2, rva01);
            ps.executeUpdate();

            ConnectionUtil.releasePreparedStatement(ps);
            ps = (PreparedStatement) conn.prepareStatement(sql_rvb);
            ps.setBigDecimal(1, price);
            ps.setBigDecimal(2, priceTax);
            ps.setString(3, rva01);
            ps.setString(4, rvb02);
            ps.executeUpdate();

            ConnectionUtil.releasePreparedStatement(ps);
            ps = (PreparedStatement) conn.prepareStatement(sql_rvu);
            ps.setLong(1, tax);
            ps.setString(2, rvu01);
            ps.executeUpdate();

            ConnectionUtil.releasePreparedStatement(ps);
            ps = (PreparedStatement) conn.prepareStatement(sql_rvv);
            ps.setBigDecimal(1, price);
            ps.setBigDecimal(2, priceTax);
            ps.setString(3, rvu01);
            ps.setString(4, rvv02);
            ps.executeUpdate();

            ConnectionUtil.releasePreparedStatement(ps);
            ps = (PreparedStatement) conn.prepareStatement(sql_up);
            ps.setString(1, pmm01);
            ps.setString(2, pmn02);
            ps.executeUpdate();

            ConnectionUtil.releasePreparedStatement(ps);
            ps = (PreparedStatement) conn.prepareStatement(sql_updatePmm40);
            ps.setString(1, pmm01);
            ps.setString(2, pmm01);
            ps.executeUpdate();

            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            release(ps, resultSet, conn);
        }

    }

    private void release(PreparedStatement ps, ResultSet rs, Connection conn) {
        ConnectionUtil.releasePreparedStatement(ps);
        ConnectionUtil.releaseResultSet(rs);
        ConnectionUtil.releaseConnection(conn);

    }

    /**
     * 更新底稿
     *
     * @param str
     */
    public void updateFak(String[] str) {

        BigDecimal price = new BigDecimal(str[2]);
        BigDecimal priceAll = new BigDecimal(str[3]);
        try {

            conn = ConnectionUtil.getConnection();
            ps = (PreparedStatement) conn.prepareStatement(sql_upfak);
            ps.setString(1, "109");
            ps.setBigDecimal(2, price);
            ps.setBigDecimal(3, priceAll);
            ps.setString(4, str[4].trim());
            ps.setString(5, str[5].trim());
            ps.setString(6, str[6].trim());
            ps.setString(7, str[7].trim());
            ps.setString(8, str[5].trim());
            ps.setBigDecimal(9, price);
            ps.setBigDecimal(10, price);
            ps.setBigDecimal(11, price);
            ps.setString(12, str[0].trim());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(ps, resultSet, conn);
        }
    }

    /**
     * 更新固资
     *
     * @param str
     */
    public void updateFaj(String[] str) {

//        BigDecimal price = new BigDecimal(str[2]);
//        BigDecimal priceAll = new BigDecimal(str[3]);
        try {

            conn = ConnectionUtil.getConnection();
            ps = (PreparedStatement) conn.prepareStatement(sql_upfaj);
            ps.setString(1, "0");
            ps.setString(2, "2018/11/10");
            ps.setString(3, "2018/11/10");
            ps.setString(4, "201812");
            ps.setString(5, "Y");
            ps.setString(6, "1");
            ps.setInt(7, 36);
            ps.setInt(8, 36);
            ps.setString(9, "Y");
            ps.setString(10, "1");
            ps.setInt(11, 36);
            ps.setInt(12, 36);
/*            ps.setString(13, "109");
            ps.setBigDecimal(14, price);
            ps.setBigDecimal(15, priceAll);
            ps.setString(16, str[4].trim());
            ps.setString(17, str[5].trim());
            ps.setString(18, str[6].trim());
            ps.setString(19, str[7].trim());
            ps.setString(20, str[5].trim());
            ps.setBigDecimal(21, price);
            ps.setBigDecimal(22, price);
            ps.setBigDecimal(23, price);
            ps.setString(24, str[5].trim());*/
            ps.setDouble(13, 389.59);
            ps.setDouble(14, 389.59);
            ps.setString(15, str[0].trim());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(ps, resultSet, conn);
        }
    }

    public void update(String[] str) {
//        String pmn02 = str[1].substring(0, str[1].indexOf("."));
        try {

            conn = ConnectionUtil.getConnection();
            ps = (PreparedStatement) conn.prepareStatement(up_pmn);
            ps.setString(1, str[1].trim());
            ps.setString(2, str[1].trim());

            ps.setString(3, str[0].trim());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(ps, resultSet, conn);
        }
    }
}

