import {Icon, SideNav, SideNavItem} from "@vaadin/react-components";

export default function SideBar() {
    return (
        <SideNav id="sideNav">
            <SideNavItem path="/questions">
                <Icon icon="vaadin:dashboard" slot="prefix"/>
                Dashboard
            </SideNavItem>
            <SideNavItem path="/inbox">
                <Icon icon="vaadin:envelope" slot="prefix"/>
                Inbox
            </SideNavItem>
            <SideNavItem path="/calendar">
                <Icon icon="vaadin:calendar" slot="prefix"/>
                Calendar
            </SideNavItem>
            <SideNavItem path="/settings">
                <Icon icon="vaadin:cog" slot="prefix"/>
                Settings
            </SideNavItem>
        </SideNav>
    );
}