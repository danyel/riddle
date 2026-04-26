// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';
import {DrawerToggle} from "@vaadin/react-components/DrawerToggle";

export default function Navigation() {
    return (
        <div slot="navbar" className={styles.navbar_container}>
            <DrawerToggle/>
            <h2 className={styles.banner}>Riddler</h2>
        </div>
    );
}