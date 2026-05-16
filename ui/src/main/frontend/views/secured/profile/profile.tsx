import {useSettingsState} from "../settings-context-provider";
import {Dialog, Grid, GridColumn, HorizontalLayout, VerticalLayout} from "@vaadin/react-components";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import {CloseButton, DeleteButton, ViewDetailButton} from "Frontend/components";
import Bookmark from "Frontend/generated/be/riddler/v1/settings/model/Bookmark";
import {BookmarkEndpoint, ParticipantAdminEndpoint, QuestionEndpoint} from "Frontend/generated/endpoints";
import {Button} from "@vaadin/react-components/Button";
import {ElementStylingTypes} from "Frontend/constant";
import {ReactNode, useState} from "react";
import {useSignal} from "@vaadin/hilla-react-signals";
import {Collections, Logs} from "Frontend/util";
import BookmarkType from "Frontend/generated/be/riddler/v1/settings/model/BookmarkType";
import Participant from "Frontend/generated/be/riddler/v1/participant/client/model/Participant";
import Question from "Frontend/generated/be/riddler/v1/question/client/model/Question";


interface DisplayModalProperties {
    children: ReactNode;
    onClose: () => void;
    isOpen: boolean;
    title: string;
}

function DisplayModal(props: DisplayModalProperties) {
    return (
        <Dialog
            headerTitle={props.title}
            opened={props.isOpen}
            onClosed={() => props.onClose()}
            footer={<CloseButton onClick={props.onClose}/>}
        >
            <div style={{minWidth: '800px', maxWidth: '1000px', minHeight: '900px', maxHeight: '1200px'}}>
                {props.children}
            </div>
        </Dialog>
    );
}

export default function ProfilePage() {
    const {settings, setSettings} = useSettingsState();
    const [bookmark, setBookmark] = useState<Bookmark>()
    const dialogOpened = useSignal(false);
    const displayModelOpened = useSignal(false);
    const title = useSignal('');
    const child = useSignal<ReactNode>(null);
    const logger = new Logs("ProfilePage");
    const bookmarksActions = ({model}: { model: { item: Bookmark } }) => {
        return (
            <>
                <DeleteButton
                    onClick={() => {
                        setBookmark(model.item);
                        dialogOpened.value = true;
                    }}
                />
                <ViewDetailButton onClick={() => {
                    const id = model.item.path.replace(`/${model.item.bookmark_type.toLocaleLowerCase()}/`, '');
                    logger.debug('Detail button clicked: [{}], {}', model.item, id);
                    if (model.item.bookmark_type === BookmarkType.PARTICIPANTS) {
                        ParticipantAdminEndpoint.findById(id)
                            .then((participant: Participant) => {
                                child.value = (
                                    <VerticalLayout theme="spacing" style={{alignItems: 'stretch'}}>
                                        <HorizontalLayout style={{
                                            justifyContent: 'space-between',
                                            borderBottom: '1px solid var(--lumo-contrast-10pct)',
                                            paddingBottom: 'var(--lumo-space-s)'
                                        }}>
                                            <span style={{color: 'var(--lumo-secondary-text-color)'}}>Name</span>
                                            <strong
                                                style={{color: 'var(--lumo-heading-text-color)'}}>{participant.first_name} {participant.last_name}</strong>
                                        </HorizontalLayout>
                                        <HorizontalLayout style={{
                                            justifyContent: 'space-between',
                                            borderBottom: '1px solid var(--lumo-contrast-10pct)',
                                            paddingBottom: 'var(--lumo-space-s)'
                                        }}>
                                            <span
                                                style={{color: 'var(--lumo-secondary-text-color)'}}>Email Address</span>
                                            <span
                                                style={{fontFamily: 'var(--lumo-font-family)'}}>{participant.email_address}</span>
                                        </HorizontalLayout>
                                        <HorizontalLayout style={{
                                            justifyContent: 'space-between',
                                            paddingTop: 'var(--lumo-space-xs)'
                                        }}>
                                            <span
                                                style={{color: 'var(--lumo-secondary-text-color)'}}>Stored Token</span>
                                            <code style={{
                                                background: 'var(--lumo-contrast-5pct)',
                                                padding: '2px 6px',
                                                borderRadius: '4px',
                                                fontSize: 'var(--lumo-font-size-s)'
                                            }}>{participant.stored_token}</code>
                                        </HorizontalLayout>
                                    </VerticalLayout>
                                );
                                displayModelOpened.value = true;
                            });
                    } else if (model.item.bookmark_type === BookmarkType.QUESTIONS) {
                        QuestionEndpoint.get(id)
                            .then((question: Question) => {
                                child.value = (
                                    <VerticalLayout theme="spacing" style={{alignItems: 'stretch'}}>
                                        <VerticalLayout theme="spacing-xs" style={{
                                            borderBottom: '1px solid var(--lumo-contrast-10pct)',
                                            paddingBottom: 'var(--lumo-space-m)'
                                        }}>
                                            <span style={{
                                                color: 'var(--lumo-secondary-text-color)',
                                                fontSize: 'var(--lumo-font-size-s)'
                                            }}>Title</span>
                                            <strong style={{
                                                color: 'var(--lumo-heading-text-color)',
                                                fontSize: 'var(--lumo-font-size-l)'
                                            }}>{question.title}</strong>
                                        </VerticalLayout>
                                        <VerticalLayout theme="spacing-xs" style={{
                                            background: 'var(--lumo-contrast-5pct)',
                                            padding: 'var(--lumo-space-m)',
                                            borderRadius: 'var(--lumo-base-border-radius)'
                                        }}>
                                            <span style={{
                                                color: 'var(--lumo-secondary-text-color)',
                                                fontSize: 'var(--lumo-font-size-s)'
                                            }}>Question Content</span>
                                            <div style={{
                                                color: 'var(--lumo-body-text-color)',
                                                lineHeight: 'var(--lumo-line-height-m)',
                                                whiteSpace: 'pre-wrap'
                                            }}>{question.question}</div>
                                        </VerticalLayout>
                                        <HorizontalLayout style={{
                                            justifyContent: 'space-between',
                                            paddingTop: 'var(--lumo-space-s)'
                                        }}>
                                            <span style={{color: 'var(--lumo-secondary-text-color)'}}>Type</span>
                                            <span style={{
                                                textTransform: 'uppercase',
                                                fontSize: 'var(--lumo-font-size-s)',
                                                fontWeight: 'bold',
                                                color: 'var(--lumo-primary-text-color)'
                                            }}>{question.type}</span>
                                        </HorizontalLayout>
                                    </VerticalLayout>
                                );
                                displayModelOpened.value = true;
                            })
                    }
                }}/>
            </>
        );
    };

    function removeBookmark() {
        BookmarkEndpoint.deleteBookmark(bookmark!!)
            .then(() => {
                    dialogOpened.value = false;
                    setSettings({
                        ...settings,
                        bookmarks: Collections.removeElement(settings.bookmarks, (e: Bookmark) => e && e.id !== bookmark!.id)
                    });
                }
            );
    }

    return (
        <VerticalLayout theme="padding spacing" className={styles.full_width_layout}>
            <HorizontalLayout theme="spacing" style={{
                alignItems: 'center',
                background: 'var(--lumo-contrast-5pct)',
                padding: 'var(--lumo-space-m)',
                borderRadius: 'var(--lumo-base-border-radius)'
            }}>
                <strong style={{fontSize: 'var(--lumo-font-size-xl)'}}>{settings.username}</strong>
                <HorizontalLayout theme="spacing-s">
                    {settings.roles?.map((role) => (
                        <span key={role} style={{
                            background: 'var(--lumo-primary-color-10pct)',
                            color: 'var(--lumo-primary-text-color)',
                            padding: '2px 8px',
                            borderRadius: '12px',
                            fontSize: 'var(--lumo-font-size-xs)',
                            fontWeight: 'bold'
                        }}>
                            {role}
                        </span>
                    ))}
                </HorizontalLayout>
            </HorizontalLayout>

            <Dialog
                headerTitle="Delete Bookmark"
                opened={dialogOpened.value}
                onClosed={() => {
                    dialogOpened.value = false;
                }}
                footer={
                    <>
                        <Button theme={ElementStylingTypes.PRIMARY_ERROR} onClick={removeBookmark}
                                style={{marginRight: 'auto'}}>
                            Delete
                        </Button>
                        <Button theme="tertiary" onClick={() => {
                            dialogOpened.value = false;
                        }}>
                            Cancel
                        </Button>
                    </>
                }
            >
                <div style={{padding: 'var(--lumo-space-xs)'}}>
                    Are you sure you want to delete the bookmark <strong>{bookmark?.label}</strong> ({bookmark?.path})
                    permanently?
                </div>
            </Dialog>

            <DisplayModal
                onClose={() => displayModelOpened.value = false}
                isOpen={displayModelOpened.value}
                title={title.value}
            >
                {child.value}
            </DisplayModal>

            <Grid items={settings.bookmarks} className={styles.riddler_table} allRowsVisible={true}>
                <GridColumn path="bookmark_type" header="Bookmark Type"/>
                <GridColumn path="path" header="Path"/>
                <GridColumn path="label" header="Label"/>
                <GridColumn header="Action" renderer={bookmarksActions}/>
            </Grid>
        </VerticalLayout>
    );
}