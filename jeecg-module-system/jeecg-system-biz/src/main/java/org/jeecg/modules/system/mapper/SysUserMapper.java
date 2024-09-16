package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.model.SysUserSysDepartModel;
import org.jeecg.modules.system.vo.SysUserDepVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @Author scott
 * @since 2018-12-20
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
	/**
	  * 通过用户账号查询用户信息
	 * @param username
	 * @return
	 */
	public SysUser getUserByName(@Param("username") String username);

	/**
	 *  根据部门Id查询用户信息
	 * @param page
	 * @param departId
     * @param username 用户登录账户
	 * @return
	 */
	IPage<SysUser> getUserByDepId(Page page, @Param("departId") String departId, @Param("username") String username);

	/**
	 *  根据用户Ids,查询用户所属部门名称信息
	 * @param userIds
	 * @return
	 */
	List<SysUserDepVo> getDepNamesByUserIds(@Param("userIds")List<String> userIds);

	/**
	 *  根据部门Ids,查询部门下用户信息
	 * @param page
	 * @param departIds
     * @param username 用户登录账户
	 * @return
	 */
	IPage<SysUser> getUserByDepIds(Page page, @Param("departIds") List<String> departIds, @Param("username") String username);

	/**
	 * 根据角色Id查询用户信息
	 * @param page
	 * @param roleId 角色id
     * @param username 用户登录账户
	 * @return
	 */
	IPage<SysUser> getUserByRoleId(Page page, @Param("roleId") String roleId, @Param("username") String username);
	
	/**
	 * 根据用户名设置部门ID
	 * @param username
	 * @param orgCode
	 */
	void updateUserDepart(@Param("username") String username,@Param("orgCode") String orgCode);
	
	/**
	 * 根据手机号查询用户信息
	 * @param phone
	 * @return
	 */
	public SysUser getUserByPhone(@Param("phone") String phone);
	
	
	/**
	 * 根据邮箱查询用户信息
	 * @param email
	 * @return
	 */
	public SysUser getUserByEmail(@Param("email")String email);

	/**
	 * 根据 orgCode 查询用户，包括子部门下的用户
	 *
	 * @param page 分页对象, xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
	 * @param orgCode
	 * @param userParams 用户查询条件，可为空
	 * @return
	 */
	List<SysUserSysDepartModel> getUserByOrgCode(IPage page, @Param("orgCode") String orgCode, @Param("userParams") SysUser userParams);


    /**
     * 查询 getUserByOrgCode 的Total
     *
     * @param orgCode
     * @param userParams 用户查询条件，可为空
     * @return
     */
    Integer getUserByOrgCodeTotal(@Param("orgCode") String orgCode, @Param("userParams") SysUser userParams);

    /**
     * 批量删除角色与用户关系
     * @Author scott
     * @Date 2019/12/13 16:10
     * @param roleIdArray
     */
	void deleteBathRoleUserRelation(@Param("roleIdArray") String[] roleIdArray);

    /**
     * 批量删除角色与权限关系
     * @Author scott
     * @Date 2019/12/13 16:10
     * @param roleIdArray
     */
	void deleteBathRolePermissionRelation(@Param("roleIdArray") String[] roleIdArray);

	/**
	 * 查询被逻辑删除的用户
     * @param wrapper
     * @return List<SysUser>
	 */
	List<SysUser> selectLogicDeleted(@Param(Constants.WRAPPER) Wrapper<SysUser> wrapper);

	/**
	 * 还原被逻辑删除的用户
     * @param userIds 用户id
     * @param entity
     * @return int
	 */
	int revertLogicDeleted(@Param("userIds") List<String> userIds, @Param("entity") SysUser entity);

	/**
	 * 彻底删除被逻辑删除的用户
     * @param userIds 多个用户id
     * @return int
	 */
	int deleteLogicDeleted(@Param("userIds") List<String> userIds);

    /**
     * 更新空字符串为null【此写法有sql注入风险，禁止随便用】
     * @param fieldName
     * @return int
     */
    @Deprecated
    int updateNullByEmptyString(@Param("fieldName") String fieldName);
    
	/**
	 *  根据部门Ids,查询部门下用户信息
	 * @param departIds
     * @param username 用户账户名称
	 * @return
	 */
	List<SysUser> queryByDepIds(@Param("departIds")List<String> departIds,@Param("username") String username);

	@Select("delete from sys_user_role where user_id in (select id from sys_user where post = 'TEM')")
	void deleteUserRole();

	@Select("delete from sys_user_depart where user_id in (select id from sys_user where post = 'TEM')")
	void deleteUserDepart();

	@Select("delete from sys_user where post = 'TEM'")
	void deleteTeamUser();

	@Select("truncate table biz_subject_balance")
	void deleteAllBalance();

	@Select("INSERT into biz_subject_balance\n" +
			"select UUID(), username, work_no, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'admin', now(), 'admin', now(), null from sys_user where username <> 'admin'")
	void insertAllBalance();

	@Select("truncate table biz_bank_config")
	void deleteBankConf();

	@Select("INSERT into biz_bank_config\n" +
			"select UUID(), u.username, d.dep_id, NULL, NULL, NULL, NULL, 'admin', now(), 'admin', now(), NULL from sys_user u join sys_user_depart d where u.id = d.user_id and u.post = 'BNK';")
	void insertBankConf();

	@Select("truncate table biz_team_resource")
	void deleteTeamResource();

	@Select("INSERT into biz_team_resource\n" +
			"select UUID(), u.username, r.resource_type, r.resource_name, 'Y', r.sale_price, 'ZC', NULL, 'admin', now(), 'admin', now(), null from sys_user u, biz_subject_resource r where u.username = r.user_id;")
	void insertTeamResource();

	@Select("truncate table biz_resource_rights")
	void deleteTeamRights();

	@Select("INSERT into biz_resource_rights\n" +
			"select UUID(), r.id, r.resource_name, y.year_code, r.user_id, 'admin', now(), 'admin', now(), null from biz_team_resource r join biz_fiscal_year y;")
	void insertTeamRights();

	@Select("update biz_fiscal_year set start_time = null, end_time = null, status = 0")
	void initFiscalYear();

	@Select("update biz_fiscal_year set start_time = now(), end_time = DATE_ADD(now(), INTERVAL 10 MINUTE), status = 1 where year_code = 1")
	void startFiscalYear();

	@Select("truncate table biz_fixed_assets_trans")
	void deleteAssetsTrans();

	@Select("truncate table biz_material_trans")
	void deleteMaterialTrans();

	@Select("truncate table biz_mine_open")
	void deleteMineOpen();

	@Select("truncate table biz_production_trans")
	void deleteProductionTrans();

	@Select("truncate table biz_transfer_acct")
	void deleteTransferAcct();

	@Select("truncate table biz_production_process")
	void deleteProductProcess();

	@Select("select t3.depart_name,count(1) count from sys_user_depart t1, sys_user t2, sys_depart t3 where t1.user_id = t2.id and t1.dep_id = t3.id and t2.post = 'TEM' group by t3.depart_name")
	List<Map<String, Object>> getTeamNumber();

	@Select("select t4.depart_name,sum(t1.cash_acct + t1.debt_claim_acct) balance from biz_subject_balance t1, sys_user t2, sys_user_depart t3, sys_depart t4 where t1.user_id = t2.username and t2.id = t3.user_id and t3.dep_id = t4.id and t2.post = 'BNK' group by t4.depart_name")
	List<Map<String, Object>> getBankBalance();
}
