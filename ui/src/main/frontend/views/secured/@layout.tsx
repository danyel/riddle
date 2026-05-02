// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';

import {AppLayout} from '@vaadin/react-components/AppLayout.js';
import {Outlet} from 'react-router';
import Navigation from "Frontend/components/navigation/Navigation";
import {VerticalLayout} from "@vaadin/react-components";
import {useState} from "react";
import SideBar from "Frontend/components/navigation/side-bar.component";
import Footer from "Frontend/components/footer/footer.component";

export default function MainLayout() {
    const [isOpened, setIsOpened] = useState(true);
    return (
        <AppLayout primarySection="navbar"
                   drawerOpened={isOpened}
                   onDrawerOpenedChanged={({detail}) => setIsOpened(detail.value)}
        >
            <Navigation participant={false}/>
            <section slot={'drawer'} className={styles.side_bar}>
                <SideBar/>
            </section>
            <main className={`${styles.content_container} ${isOpened ? 'drawer-is-open' : 'drawer-is-closed'}`}>
                <VerticalLayout className={styles.content}>
                    <Outlet/>
                </VerticalLayout>
                <Footer/>
            </main>
        </AppLayout>
    );
}

