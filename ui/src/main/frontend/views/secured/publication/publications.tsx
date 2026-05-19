import {useEffect, useState} from "react";
import Publication from "Frontend/generated/be/riddler/v1/publication/client/model/Publication";
import {
    Button,
    Dialog,
    Grid,
    GridColumn,
    HorizontalLayout,
    TextArea,
    TextField,
    VerticalLayout
} from "@vaadin/react-components";
// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css'
import {useSignal} from "@vaadin/hilla-react-signals";
import {CancelButton, CheckButton, ViewDetailButton} from "Frontend/components";
import CreatePublication from "Frontend/generated/be/riddler/v1/publication/client/model/CreatePublication";
import {LevelSelector} from "Frontend/components/publication/level-selector-component";
import {PositionSelector} from "Frontend/components/publication/position-selector-component";
import {Notify, Urls} from "Frontend/util";
import {ElementStylingTypes} from "Frontend/constant";
import {PublicationsEndpoint} from "Frontend/generated/endpoints";
import BookmarkType from "Frontend/generated/be/riddler/v1/settings/model/BookmarkType";
import {useNavigate} from "react-router";
import FormItem from "Frontend/components/ui/form/form-item.component";
import Empty from "Frontend/components/ui/empty/empty";

export default function PublicationsPage() {
    const [publications, setPublications] = useState<Publication[]>([]);
    const createPublicationOpened = useSignal(false);
    const createPublication = useSignal<CreatePublication>({
        description: '',
        title: '',
        proposal: '',
        level_id: '',
        position_id: ''
    });
    const navigate = useNavigate();

    useEffect(() => {
        if (!createPublicationOpened.value) {
            getPublications();
        }
    }, [createPublicationOpened.value]);

    function getPublications() {
        PublicationsEndpoint.getPublications()
            .then(setPublications);
    }

    const actionButtons = ({item}: { item: Publication }) => {
        return (
            <>
                <ViewDetailButton onClick={() => {
                    navigate(Urls.makePath(BookmarkType.PUBLICATIONS, item.id));
                }}/>
            </>
        );
    };

    return (
        <VerticalLayout className={styles.full_width_layout}>
            <HorizontalLayout className={styles.full_width_layout}>
                <div className={styles.menu_bar_layout}>
                    <Button theme="primary" onClick={() => createPublicationOpened.props = true}>Add New
                        Publication</Button>
                </div>
            </HorizontalLayout>
            <HorizontalLayout className={styles.full_width_layout}>
                <Dialog headerTitle="Delete Bookmark"
                        opened={createPublicationOpened.value}
                        onClosed={() => {
                            createPublicationOpened.value = false;
                        }}
                        footer={
                            <>
                                <CheckButton onClick={() => {
                                    const createPublicationVa = createPublication.value;
                                    PublicationsEndpoint.createPublication(createPublicationVa)
                                        .then((publication) => {
                                            Notify.success('Publication created successfully');
                                            setPublications([publication, ...publications]);
                                            createPublicationOpened.value = false;
                                        })
                                        .catch(err => Notify.error('Failed to save publication: {}', err));
                                }}/>
                                <CancelButton theme={ElementStylingTypes.ERROR} onClick={() => {
                                    createPublicationOpened.value = false;
                                }}/>
                            </>
                        }>
                    <VerticalLayout theme="spacing" style={{alignItems: 'stretch'}}>
                        <FormItem
                            children={
                                <TextField
                                    label="Title"
                                    value={createPublication.value.title}
                                    onValueChanged={(e) => (createPublication.value.title = e.detail.value)}
                                    className={styles.text_area_full}
                                />
                            }
                        />
                        <FormItem
                            children={
                                <TextArea
                                    label="Description"
                                    value={createPublication.value.description}
                                    onValueChanged={(e) => (createPublication.value.description = e.detail.value)}
                                    className={styles.text_area_full}
                                />
                            }
                        />

                        <FormItem
                            children={
                                <TextArea
                                    label="Proposal"
                                    value={createPublication.value.proposal}
                                    onValueChanged={(e) => (createPublication.value.proposal = e.detail.value)}
                                    className={styles.text_area_full}
                                />
                            }
                        />

                        <FormItem
                            children={
                                <LevelSelector
                                    selectedLevelId={createPublication.value.level_id}
                                    onLevelChange={(id) => createPublication.value.level_id = id!!}
                                />
                            }
                        />

                        <FormItem
                            children={
                                <PositionSelector
                                    selectedPositionId={createPublication.value.position_id}
                                    onPositionChange={(id) => createPublication.value.position_id = id!!}
                                />
                            }
                        />
                    </VerticalLayout>
                </Dialog>
                {publications.length > 0 ?
                    (
                        <Grid key={"id"} items={publications} className={styles.riddler_table} allRowsVisible={true}>
                            <GridColumn key={"title"} path={"title"}/>
                            <GridColumn key={"description"} path={"description"}/>
                            <GridColumn key={"position"} path={"position.position"}/>
                            <GridColumn key={"level"} path={"level.level"}/>
                            <GridColumn header={'Actions'} renderer={actionButtons}/>
                        </Grid>) :
                    (
                        <Empty emptyMessage={"No publication found"}
                               helperMessage={"Click \"Add New Publication\" to add a new publication."}/>
                    )}

            </HorizontalLayout>
        </VerticalLayout>
    );
}