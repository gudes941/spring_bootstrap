package kr.or.ddit.service;

import java.sql.SQLException;
import java.util.List;

import com.jsp.dto.MenuVO;
import com.jsp.service.MenuService;

import kr.or.ddit.dao.MenuDAOBean;

public class MenuServiceImpl implements MenuService {
	
	private MenuDAOBean menuDAOBean;// = new MenuDAOImpl();
	public void setMenuDAO(MenuDAOBean menuDAOBean) {
		this.menuDAOBean = menuDAOBean;
	}
	
	@Override
	public List<MenuVO> getMainMenuList() throws SQLException {
		List<MenuVO> menuList = null;
		menuList = menuDAOBean.selectMainMenu();
		return menuList;
	}

	@Override
	public MenuVO getMenuByMcode(String mCode) throws SQLException {
		MenuVO menu = null;
		menu = menuDAOBean.selectMenuByMcode(mCode);
		return menu;
	}

	@Override
	public MenuVO getMenuByMname(String mName) throws SQLException {
		MenuVO menu = null;
		menu = menuDAOBean.selectMenuByMname(mName);
		return menu;
	}

	@Override
	public List<MenuVO> getSubMenuList(String mCode) throws SQLException {
		List<MenuVO> menuList = null;
		menuList = menuDAOBean.selectSubMenu(mCode);
		return menuList;
	}

}
