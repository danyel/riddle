import {Icon, SideNav, SideNavItem} from "@vaadin/react-components";
import {useEffect, useState} from "react";
import {MenuService} from "Frontend/generated/endpoints";
import Menu from "Frontend/generated/be/riddler/v1/menu/client/model/Menu";
import {useNavigate} from "react-router";

export default function SideBar() {
    const [menus, setMenus] = useState<Menu[]>([]);
    const navigate = useNavigate();

    useEffect(() => {
        MenuService.menu()
            .then(setMenus);
    }, []);

    return (
        <SideNav id="sideNav">
            {menus.map((menu, index) => {
                const menuPath = menu.path!!;
                const cleanMenuHash = menuPath.startsWith('/#') ? menuPath.substring(1) : menuPath;
                const routingPath = cleanMenuHash.replace(/^#/, '');
                return (
                    <SideNavItem
                        key={menuPath}
                        ref={(el) => {
                            const activeHash = location.hash || '#/';
                            const isCurrent = activeHash === cleanMenuHash;

                            if (el) {
                                if (isCurrent) {
                                    el.setAttribute('current', '');
                                } else {
                                    el.removeAttribute('current');
                                }
                            }
                        }}
                        onClick={(e) => {
                            e.preventDefault();
                            navigate(routingPath);
                        }}
                    >
                        <Icon icon={menu.icon} slot="prefix"/>
                        {menu.label}
                    </SideNavItem>
                );
            })}
        </SideNav>
    );
}
