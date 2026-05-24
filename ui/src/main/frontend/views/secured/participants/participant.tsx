import {Dialog, FormLayout, HorizontalLayout, Select} from "@vaadin/react-components";
import {useParams} from "react-router";
import {useEffect, useState} from "react";
import {InvitationEndpoint, ParticipantAdminEndpoint, PublicationsEndpoint} from "Frontend/generated/endpoints";
import Participant from "Frontend/generated/be/riddler/v1/participant/client/model/Participant";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import {CloseButton, PlusButton} from "Frontend/components/ui/button";
import {Button} from "@vaadin/react-components/Button";
import {ElementStylingTypes} from "Frontend/constant";
import {Notify} from "Frontend/util";
import Invitation from "Frontend/generated/be/riddler/v1/invitation/client/model/Invitation";
import Publication from "Frontend/generated/be/riddler/v1/publication/client/model/Publication";
import FormItem from "Frontend/components/ui/form/form-item.component";
import RiddlerModal from "Frontend/components/ui/modal/modal";
import ParticipantProfileDetail from "Frontend/components/participant/participant";
import PublicationDetail from "Frontend/components/publication/publication";
import {Ban, Newspaper, Save, Trash2} from "lucide-react";
import RiddlerTable from "Frontend/components/table/table";

export type ModalType = 'PUBLICATION' | 'INVITATION' | 'CV' | 'PHOTO' | 'TODO' | 'NONE';

export function AdminParticipant() {
    const [modalType, setModalType] = useState<ModalType>('NONE');
    const [open, setOpen] = useState(false);
    const [publicationId, setPublicationId] = useState<string>();
    const [publications, setPublications] = useState<Publication[]>([]);
    const [publication, setPublication] = useState<Publication>();
    const [publicationIds, setPublicationIds] = useState<string[]>([]);
    const params = useParams();
    const [participant, setParticipant] = useState<Participant>();
    const [invitations, setInvitations] = useState<Invitation[]>([]);

    function fetchInvitations() {
        if (params.id) {
            InvitationEndpoint.findInvitationsByParticipantId(params.id)
                .then(invitations => {
                    setPublicationIds(invitations.map(e => e.publication.id));
                    setInvitations(invitations);
                    if (participant) {
                        setPublications(publications.filter(e => publicationIds.indexOf(e.id) > 0));
                    }
                })
        }
    }

    useEffect(() => {
        if (params.id) {
            PublicationsEndpoint.getPublications()
                .then((elements: Publication[]) => {
                    ParticipantAdminEndpoint.findById(params.id!!)
                        .then(participant => {
                            fetchInvitations();
                            setParticipant(participant);
                            if (publicationIds.length > 0) {
                                setPublications(elements.filter(e => publicationIds.indexOf(e.id) > 0));
                            } else {
                                setPublications(elements);
                            }
                        });
                });


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

    function closeModal() {
        setModalType('NONE')
        setOpen(false);
    }

    function openModal(publicationId: string, modalType: ModalType) {
        setPublicationId(publicationId);
        if (publicationId) {
            PublicationsEndpoint.findPublicationById(publicationId)
                .then((publication) => {
                    setPublication(publication);
                    setOpen(true);
                });
        }
        setModalType(modalType);
        setOpen(true);
    }

    useEffect(() => {
        if (publicationId) {
            PublicationsEndpoint.findPublicationById(publicationId)
                .then(setPublication);
        }
    }, [publicationId]);

    function createInvitation(participantId: string) {
        const createInvitation = {participant_id: participantId, publication_id: publicationId!!};
        InvitationEndpoint.create(createInvitation)
            .then((invitation: Invitation) => {
                Notify.success('Invitation {} created', invitation.id);
                fetchInvitations();
                setOpen(false);
            });
    }

    return (
        <>
            <RiddlerModal
                headerTitle={publication?.title}
                opened={open && modalType === 'PUBLICATION'}
                onClosed={() => setOpen(false)}
                footer={
                    <CloseButton onClick={() => setOpen(false)}/>
                }
                content={
                    <PublicationDetail publication={publication!!}/>
                }/>
            <RiddlerModal
                headerTitle={`Participant: ${participant?.first_name} ${participant?.last_name}`}
                opened={open && modalType === 'CV'}
                // 💡 Removed the old inline ref block completely
                onClosed={() => setOpen(false)}
                footer={
                    <CloseButton onClick={() => setOpen(false)}/>
                }
                content={
                    <>
                        {open && modalType === 'CV' && participant?.cv && (
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
                    </>
                }
            />
            {participant && (
                <ParticipantProfileDetail
                    openModal={openModal}
                    setParticipant={setParticipant}
                    participant={participant}
                />
            )}
            <Dialog
                headerTitle="TODO"
                opened={open && modalType === 'TODO'}
                onClosed={() => {
                    closeModal();
                }}
                footer={
                    <>
                        <Button theme={ElementStylingTypes.PRIMARY_ERROR} onClick={closeModal}
                                style={{marginRight: 'auto'}}>
                            Delete
                        </Button>
                        <Button theme="tertiary" onClick={() => {
                            closeModal();
                        }}>
                            Cancel
                        </Button>
                    </>
                }
            >
                <div style={{padding: 'var(--lumo-space-xs)'}}>
                    TODO
                </div>
            </Dialog>
            <RiddlerModal
                headerTitle="Create invitation"
                opened={open && modalType === 'INVITATION'}
                onClosed={() => {
                    setOpen(false);
                }}
                footer={
                    <>
                        <Button theme={ElementStylingTypes.TERTIARY_ICON} onClick={() => {
                            createInvitation(params.id!!)
                        }}>
                            <Save size={24}/>
                        </Button>
                        <Button theme={ElementStylingTypes.TERTIARY_ICON_RED} onClick={() => setOpen(false)}>
                            <Ban size={24}/>
                        </Button>
                    </>
                }
                content={
                    <>
                        <FormLayout style={{width: '100%'}}
                                    autoResponsive
                                    columnWidth="8em"
                                    expandColumns
                                    expandFields>
                            <FormItem key={"publication"}
                                      children={
                                          <Select
                                              label="Choose publication"
                                              items={publications.map(pub => ({label: pub.title, value: pub.id}))}
                                              value={publicationId}
                                              onValueChanged={(e) => {
                                                  setPublicationId(e.detail.value);
                                              }}
                                          />
                                      }/>

                            <div style={{colspan: 2, marginTop: 'var(--lumo-space-m)'}}>
                                <div style={{
                                    fontSize: 'var(--lumo-font-size-s)',
                                    fontWeight: 500,
                                    color: 'var(--lumo-secondary-text-color)',
                                    marginBottom: 'var(--lumo-space-xs)'
                                }}>
                                    Description
                                </div>

                                <div className={styles.modal_text_area_ro}>
                                    {publication?.description || (
                                        <span style={{color: 'var(--lumo-disabled-text-color)', fontStyle: 'italic'}}>
                                            No description available for this publication.
                                        </span>
                                    )}
                                </div>
                            </div>
                        </FormLayout>

                    </>
                }
            />
            <HorizontalLayout className={styles.full_width_layout}>
                <div className={styles.menu_bar_layout}>
                    <PlusButton onClick={() => {
                        setModalType('INVITATION');
                        setOpen(true);
                    }} disabled={publications.length === 0}/>
                </div>
            </HorizontalLayout>
            <HorizontalLayout className={styles.full_width_layout}>
                <RiddlerTable elements={invitations}
                              columnNames={
                                  [
                                      {
                                          path: 'id',
                                          flexGrow: 0,
                                          width: "140px",
                                          renderer: ({item}) => (
                                              <code
                                                  title={item.id}
                                                  style={{
                                                      background: 'var(--lumo-contrast-5pct)',
                                                      color: 'var(--lumo-primary-text-color)',
                                                      padding: '2px 6px',
                                                      borderRadius: '4px',
                                                      fontSize: 'var(--lumo-font-size-xs)',
                                                      fontFamily: 'var(--lumo-font-family-monospace)',
                                                      fontWeight: 'bold',
                                                      textTransform: 'uppercase'
                                                  }}
                                              >
                                                  {item.id ? `${item.id.substring(0, 8)}...` : 'N/A'}
                                              </code>
                                          )
                                      },
                                      {
                                          path: 'publication.title',
                                          flexGrow: 1,
                                          width: '200px',
                                          renderer: ({item}) => (
                                              <div style={{
                                                  fontWeight: 500,
                                                  color: 'var(--lumo-heading-text-color)',
                                                  fontSize: 'var(--lumo-font-size-m)',
                                                  whiteSpace: 'normal', // Forces text to wrap beautifully instead of overflowing columns
                                                  wordBreak: 'break-word',
                                                  lineHeight: 'var(--lumo-line-height-m)'
                                              }}>
                                                  {item.publication?.title || (
                                                      <span style={{
                                                          color: 'var(--lumo-disabled-text-color)',
                                                          fontStyle: 'italic'
                                                      }}>
                                                          Untitled Publication
                                                      </span>
                                                  )}
                                              </div>
                                          )
                                      }
                                  ]
                              }
                              emptyMessage={'No active invitations found'}
                              helperMessage={'Sent or pending invitations will appear here in this list view.'}
                              actionButtons={({item}: { item: Invitation }) => {
                                  return (
                                      <>
                                          <Button theme={ElementStylingTypes.TERTIARY_ICON}
                                                  onClick={() => openModal(item.publication.id, 'PUBLICATION')}><Newspaper/></Button>
                                          <Button theme={ElementStylingTypes.TERTIARY_ICON_RED}
                                                  onClick={() => openModal(item.publication.id, 'TODO')}><Trash2/></Button>
                                      </>
                                  );
                              }}
                />
            </HorizontalLayout>
        </>
    )
}