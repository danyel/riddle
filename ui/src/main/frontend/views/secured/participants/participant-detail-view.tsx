import {
    Card,
    Dialog,
    Grid,
    GridColumn,
    HorizontalLayout,
    Upload,
    UploadBeforeEvent,
    VerticalLayout
} from "@vaadin/react-components";
import {useParams} from "react-router";
import {useEffect, useState} from "react";
import {InvitationEndpoint, ParticipantAdminEndpoint} from "Frontend/generated/endpoints";
import ParticipantDetail from "Frontend/generated/be/riddler/v1/participant/client/domain/ParticipantDetail";
import InvitationDetail from "Frontend/generated/be/riddler/v1/invitation/client/model/InvitationDetail";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import {PlusButton} from "Frontend/components/ui/button";
import {Button} from "@vaadin/react-components/Button";
import {ElementStylingTypes} from "Frontend/constant";

export function ParticipantDetailView() {
    const [cvOpen, setCvOpen] = useState(false);
    const [open, setOpen] = useState(false);
    const params = useParams();
    const [participant, setParticipant] = useState<ParticipantDetail>();
    const [invitations, setInvitations] = useState<InvitationDetail[]>([]);

    function fetchInvitations() {
        if (params.id) {
            InvitationEndpoint.findInvitationsByParticipantId(params.id)
                .then(setInvitations)
        }
    }

    useEffect(() => {
        if (params.id) {
            ParticipantAdminEndpoint.findById(params.id)
                .then(setParticipant)

            fetchInvitations();
        }
    }, [params.id]);

    function getCookie(name: string): string | null {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop()?.split(';').shift() || null;
        return null;
    }

    function createInvitation(participantId: string) {
        InvitationEndpoint.create({participantId: participantId})
            .then(() => {
                fetchInvitations();
                setOpen(false);
            });
    }

    return (
        <>
            <Dialog
                headerTitle="Curriculum Vitae Preview"
                opened={cvOpen}
                ref={(el) => {
                    if (el) {
                        // Force Vaadin's underlying overlay container to stretch to full viewport width/height
                        el.style.setProperty('--vaadin-dialog-width', '100vw');
                        el.style.setProperty('--vaadin-dialog-height', '100vh');
                    }
                }}
                onClosed={() => setCvOpen(false)}
                // 1. Force the dialog window to expand significantly on the viewport surface
                footer={
                    <Button theme="tertiary" onClick={() => setCvOpen(false)}>
                        Close
                    </Button>
                }
            >
                {/* 2. Wrap the viewer frame container inside a flex box layout */}
                <div style={{width: '100%', height: '100%', display: 'flex', boxSizing: 'border-box'}}>
                    {cvOpen && participant?.cv && (
                        <iframe
                            // 💡 Pass the base64 string directly as an application/pdf Data URL context scheme
                            src={`data:application/pdf;base64,${participant.cv}`}
                            title="CV Document Viewer"
                            style={{
                                width: '100%',
                                height: '100%',
                                minHeight: '68vh',
                                border: 'none',
                                borderRadius: 'var(--lumo-space-s)'
                            }}
                        />
                    )}
                </div>
            </Dialog>
            <HorizontalLayout className={styles.full_width_layout}>
                <VerticalLayout className={styles.full_width_layout}>
                    <HorizontalLayout className={styles.full_width_layout}>
                        <Card>
                            {participant?.photo != undefined && (
                                <img slot="media" width="100" src={`data:image/png;base64,${participant?.photo}`}
                                     alt={"Photo"}/>)}
                            <div>{participant?.first_name}</div>
                            <div>{participant?.last_name}</div>
                            <div>{participant?.email_address}</div>
                            <HorizontalLayout className="w-full gap-m">
                                <Button
                                    theme="secondary"
                                    disabled={!participant?.cv}
                                    onClick={() => setCvOpen(true)}
                                >
                                    View CV Document
                                </Button>
                            </HorizontalLayout>
                        </Card>
                    </HorizontalLayout>
                    <VerticalLayout className={styles.full_width_layout}>
                        <HorizontalLayout className={styles.full_width_layout}>
                            <Upload
                                accept="image/*"
                                target={"/v1/participants/" + params.id!! + "/photo"}
                                maxFileSize={20000000}
                                nodrop
                                onUploadBefore={(e: UploadBeforeEvent) => {
                                    const csrfToken = getCookie('XSRF-TOKEN');
                                    if (csrfToken) {
                                        // 💡 Inject the CSRF token into the h <Button theme="secondary" onClick={() => setCvOpen(true)}>
                                        //                                     View CV Document
                                        //                                 </Button>eader to satisfy Spring Security 7.0
                                        e.detail.xhr.setRequestHeader('X-XSRF-TOKEN', csrfToken);
                                    }
                                }}
                            />
                        </HorizontalLayout>
                        <HorizontalLayout className={styles.full_width_layout}>
                            <Upload
                                accept="application/pdf"
                                target={"/v1/participants/" + params.id!! + "/cv"}
                                maxFileSize={20000000}
                                nodrop
                                onUploadBefore={(e: UploadBeforeEvent) => {
                                    const csrfToken = getCookie('XSRF-TOKEN');
                                    if (csrfToken) {
                                        // 💡 Inject the CSRF token into the header to satisfy Spring Security 7.0
                                        e.detail.xhr.setRequestHeader('X-XSRF-TOKEN', csrfToken);
                                    }
                                }}
                            />
                        </HorizontalLayout>
                    </VerticalLayout>
                </VerticalLayout>
            </HorizontalLayout>
            <Dialog
                headerTitle={`Create invitation"?`}
                opened={open}
                onClosed={() => {
                    setOpen(false);
                }}
                footer={
                    <>
                        <Button theme={ElementStylingTypes.PRIMARY_ERROR} onClick={() => {
                            createInvitation(params.id!!)
                        }}
                                style={{marginRight: 'auto'}}>
                            Create Invitation
                        </Button>
                        <Button theme="tertiary" onClick={close}>
                            Cancel
                        </Button>
                    </>
                }
            >
                Are you sure you want to delete this user permanently?
            </Dialog>
            <HorizontalLayout className={styles.full_width_layout}>
                <div className={styles.menu_bar_layout}>
                    <PlusButton onClick={() => setOpen(true)}/>
                </div>
            </HorizontalLayout>
            <HorizontalLayout className={styles.full_width_layout}>
                <InvitationTable invitations={invitations}/>
            </HorizontalLayout>
        </>
    )
}

function InvitationTable(props: { invitations: InvitationDetail[] }) {
    return (
        <Grid items={props.invitations} className={styles.riddler_table} allRowsVisible={true}>
            <GridColumn path="id" header="Invitation Id"/>
        </Grid>
    );
}