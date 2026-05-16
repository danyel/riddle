import {useEffect, useState} from 'react';
import {Dialog, FormLayout, Grid, GridColumn, HorizontalLayout, Select, TextArea} from "@vaadin/react-components";
// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';

// CRITICAL: You must import the iconset for the icons to render
import '@vaadin/icons';
import {useNavigate} from "react-router";
import Question from "Frontend/generated/be/riddler/v1/question/client/model/Question";
import CreateQuestion from "Frontend/generated/be/riddler/v1/question/client/model/CreateQuestion";
import {QuestionEndpoint} from "Frontend/generated/endpoints";
import {CancelButton, CheckButton, CloseButton, PlusButton, ViewDetailButton} from "Frontend/components/ui/button";
import QuestionType from "Frontend/generated/be/riddler/v1/question/client/model/QuestionType";
import {Urls} from "Frontend/util";
import BookmarkType from "Frontend/generated/be/riddler/v1/settings/model/BookmarkType";
import FormItem from "Frontend/components/ui/form/form-item.component";


function CreateQuestionDialogModal(props: {
    show: boolean;
    onParticipantCreated: () => void;
    onClose: () => void;
}) {
    const [createQuestion, setCreateQuestion] = useState<CreateQuestion>({
        type: QuestionType.OPEN,
        question: '',
        title: ''
    });
    const dropDown: { label: string, value: QuestionType }[] = [
        {
            label: 'Open',
            value: QuestionType.OPEN
        },
        {
            label: 'Review',
            value: QuestionType.REVIEW
        },
        {
            label: 'Single Choice',
            value: QuestionType.SINGLE_CHOICE
        },
        {
            label: 'Multiple Choice',
            value: QuestionType.MULTIPLE_CHOICE
        }
    ];

    useEffect(() => {
        if (props.show) {
            // Clear input value when the modal opens
            createQuestion.type = QuestionType.OPEN;
            createQuestion.question = '';
            createQuestion.title = '';
        }
    }, [props.show]);

    function saveQuestion() {
        const payload: CreateQuestion = {
            question: createQuestion.question,
            type: createQuestion.type,
            title: createQuestion.title,
        };
        QuestionEndpoint.create(payload)
            .then(() => {
                props.onParticipantCreated();
                props.onClose();
            });
    }

    function closeIfNotValue(e: CustomEvent<{ value: boolean }>) {
        // Syncs background clicks / ESC keys directly back to parent
        if (!e.detail.value) props.onClose();
    }

    return (
        <Dialog
            width={"100vh"}
            height={"100vh"}
            header-title="Create question"
            opened={props.show}
            onOpenedChanged={closeIfNotValue}
            header={<CancelButton onClick={() => props.onClose()}/>}
            footerRenderer={() => (
                <>
                    <CloseButton onClick={props.onClose}/>
                    <CheckButton onClick={saveQuestion}/>
                </>
            )}
        >
            <FormLayout
                style={{width: '100%'}}
                autoResponsive
                columnWidth="8em"
                expandColumns
                expandFields
            >

                <FormItem children={<Select label="Type" items={dropDown}
                                            onValueChanged={(e) => {

                                                setCreateQuestion(prevState => {
                                                    return {
                                                        ...prevState,
                                                        type: e.detail.value as QuestionType
                                                    }
                                                });
                                            }}/>}/>
                <FormItem key={"question"} children={
                    <TextArea value={createQuestion.question}
                              className={styles.text_area_full}
                              onValueChanged={(e) => {
                                  setCreateQuestion(prev => {
                                      return {
                                          ...prev,
                                          question: e.detail.value as QuestionType
                                      }
                                  });
                              }}
                    />}/>

            </FormLayout>
        </Dialog>
    );
}

function QuestionTable(props: { questions: Question[] }) {
    const navigate = useNavigate();
    const showQuestion = ({item}: { item: Question }) => {
        return <ViewDetailButton onClick={() => navigate(Urls.makePath(BookmarkType.QUESTIONS, item.id))}/>;
    };
    return (
        <Grid key={"id"} items={props.questions} className={styles.riddler_table} allRowsVisible={true}>
            <GridColumn key={"title"} path={"title"}/>
            <GridColumn key={"question"} path={"question"}/>
            <GridColumn key={"type"} path={"type"}/>
            <GridColumn header={'Action'} renderer={showQuestion}/>
        </Grid>
    );
}

export default function QuestionsView() {
    const [open, setOpen] = useState(false);
    const [questions, setQuestions] = useState<Question[]>([]);

    function fetchQuestions() {
        QuestionEndpoint.getQuestions().then(setQuestions);
    }

    useEffect(() => {
        fetchQuestions();
    }, []);

    return (
        <>
            <HorizontalLayout className={styles.full_width_layout}>
                <div className={styles.menu_bar_layout}>
                    <PlusButton onClick={() => setOpen(true)}/>
                </div>
            </HorizontalLayout>
            <CreateQuestionDialogModal show={open}
                                       onParticipantCreated={fetchQuestions}
                                       onClose={() => setOpen(false)}/>
            <HorizontalLayout className={styles.div_full}>
                <QuestionTable questions={questions}/>
            </HorizontalLayout>
        </>
    );
}