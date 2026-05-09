import {Icon, SideNav, SideNavItem} from "@vaadin/react-components";
import {useEffect, useState} from "react";
import {MenuService} from "Frontend/generated/endpoints";
import Menu from "Frontend/generated/be/riddler/v1/menu/domain/Menu";

export default function SideBar() {
    const [menus, setMenus] = useState<Menu[]>([]);

    useEffect(() => {
        MenuService.menu()
            .then(setMenus);
    }, []);

    return (
        <SideNav id="sideNav">
            {menus.map(menu => {
                return (
                    <SideNavItem key={menu.path} path={menu.path}>
                        <Icon icon={menu.icon} slot="prefix"/>
                        {menu.label}
                    </SideNavItem>
                );
            })}
        </SideNav>
    );
}
