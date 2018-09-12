package com.wk.config;/**
 * Created by yhopu-pc2 on 2018/7/18.
 */

import com.wk.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;


import java.util.*;

/**
 * @author wk
 * @className AuthRealm
 **/
public class AuthRealm extends AuthorizingRealm {
    /**
     * 只有需要验证权限时才会调用，授权查询回调函数
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute("USER_SESSION");
        //权限信息对象info，用来存放查出来的用户所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //用户的角色集合
        Set<String> roles = new HashSet<>();
        roles.add(user.getRoleName());
        info.setRoles(roles);
        //用户的角色对应的所有权限，如果只使用角色定义访问权限，下面可以不要
        //只有角色并没有颗粒度到每一个按钮或是操作选项
        final Map<String, Collection<String>> permissionCache = DBCache.PERMISSIONS_CACHE;
        final Collection<String> permissions = permissionCache.get(user.getRoleName());
        info.addStringPermissions(permissions);
        return info;
    }

    /**
     * 认证回调函数，登录时调用
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = (String) token.getPrincipal();
        User user = Optional.ofNullable(DBCache.USERS_CACHE.get(principal)).orElseThrow(UnknownAccountException::new);
        if (!user.isLocked()) {
            throw new LockedAccountException();
        }
        // 从数据库查询出来的账号名和密码，与用户输入的账号和密码对比
        // 当用户执行登录时，在方法处理上要实现 user.login(token)
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(principal, user.getPassword(), user.getUsername());
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("USER_SESSION", user);
        return authenticationInfo;
    }
}
