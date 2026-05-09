// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';
import {DrawerToggle} from "@vaadin/react-components/DrawerToggle";

export interface NavigationProperties {
    noMenu: boolean
}

export default function Navigation(props: NavigationProperties) {
    return (
        <div slot="navbar" className={styles.navbar_container}>
            {
                !props.noMenu && (
                    <DrawerToggle/>
                )
            }
            <h2 className={styles.banner}>Riddler</h2>
        </div>
    );
}