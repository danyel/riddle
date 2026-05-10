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
import ParticipantDetail from "Frontend/generated/be/riddler/v1/participant/client/model/ParticipantDetail";
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
            requestAnimationFrame(() => {
                // noinspection CssInvalidHtmlTagReference
                const overlay = document.querySelector('vaadin-dialog-overlay');
                if (overlay) {
                    // Pierce the component parts to strip padding and force absolute fullscreen dimensions
                    const partOverlay = overlay.shadowRoot?.querySelector('[part="overlay"]') as HTMLElement;
                    const partContent = overlay.shadowRoot?.querySelector('[part="content"]') as HTMLElement;

                    if (partOverlay) {
                        partOverlay.style.setProperty('width', '80vw', 'important');
                        partOverlay.style.setProperty('height', '80vh', 'important');
                        partOverlay.style.setProperty('max-width', '80vw', 'important');
                        partOverlay.style.setProperty('max-height', '80vh', 'important');
                    }
                    if (partContent) {
                        // Strips default padding around your iframe container box
                        partContent.style.setProperty('padding', '40', 'important');
                    }
                }
            });
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
                // 💡 Removed the old inline ref block completely
                onClosed={() => setCvOpen(false)}
                footer={
                    <Button theme="tertiary" onClick={() => setCvOpen(false)} style={{margin: 'var(--lumo-space-m)'}}>
                        Close
                    </Button>
                }
            >
                {/* Flex configuration forces iframe to consume 100% of the newly expanded overlay space */}
                <div style={{
                    width: '90vw',
                    height: 'calc(90vh - 120px)', // Account for the header and footer bar heights
                    display: 'flex',
                    flexDirection: 'column',
                    boxSizing: 'border-box'
                }}>
                    {cvOpen && participant?.cv && (
                        <iframe
                            src={`data:application/pdf;base64,${participant.cv}`}
                            title="CV Document Viewer"
                            style={{
                                width: '100%',
                                height: '100%',
                                flex: '1 1 auto',
                                border: 'none'
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
                                        // 💡 FIXED: Cleaned up the broken inline button comment text block
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
                                        e.detail.xhr.setRequestHeader('X-XSRF-TOKEN', csrfToken);
                                    }
                                }}
                            />
                        </HorizontalLayout>
                    </VerticalLayout>
                </VerticalLayout>
            </HorizontalLayout>
            <Dialog
                headerTitle="Create invitation"
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
                        <Button theme="tertiary" onClick={() => setOpen(false)}>
                            Cancel
                        </Button>
                    </>
                }
            >
                Are you sure you want to create an invitation for this user?
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
