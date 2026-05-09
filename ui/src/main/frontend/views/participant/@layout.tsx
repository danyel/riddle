// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';

import {AppLayout} from '@vaadin/react-components/AppLayout.js';
import {Outlet} from 'react-router';
import Navigation from "Frontend/components/navigation/Navigation";
import {VerticalLayout} from "@vaadin/react-components";
import Footer from "Frontend/components/footer/footer.component";

export default function ParticipantLayout() {
    return (
        <AppLayout>
            <Navigation noMenu={true}/>
            <main className={`${styles.content_container} drawer-is-closed`}>
                <VerticalLayout className={styles.content}>
                    <Outlet/>
                </VerticalLayout>
                <Footer/>
            </main>
        </AppLayout>
    );
}

