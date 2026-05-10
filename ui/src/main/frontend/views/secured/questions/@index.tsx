import {useEffect, useState} from 'react';
import {
    Dialog,
    FormLayout,
    FormRow,
    Grid,
    GridColumn,
    HorizontalLayout,
    Select,
    TextField
} from "@vaadin/react-components";
// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';

// CRITICAL: You must import the iconset for the icons to render
import '@vaadin/icons';
import {useNavigate} from "react-router";
import Question from "Frontend/generated/be/riddler/v1/question/client/model/Question";
import CreateQuestion from "Frontend/generated/be/riddler/v1/question/client/model/CreateQuestion";
import {QuestionEndpoint} from "Frontend/generated/endpoints";
import {CancelButton, CheckButton, CloseButton, PlusButton, ViewDetailButton} from "Frontend/components/ui/button";
import {useSignal} from "@vaadin/hilla-react-signals";
import QuestionType from "Frontend/generated/be/riddler/v1/question/client/model/QuestionType";


function CreateQuestionDialogModal(props: {
    show: boolean;
    onParticipantCreated: () => void;
    onClose: () => void;
}) {
    const createQuestion = useSignal<CreateQuestion>({type: QuestionType.OPEN, question: ''});
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
            createQuestion.value.type = QuestionType.OPEN;
            createQuestion.value.question = '';
        }
    }, [props.show]);

    function saveQuestion() {
        const payload: CreateQuestion = {
            question: createQuestion.value.question,
            type: createQuestion.value.type,
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
                <FormRow>
                    <TextField
                        label="Question"
                        value={createQuestion.value.question}
                        onValueChanged={(e) => createQuestion.value.question = e.detail.value}
                        className={styles.text_area_full}
                    />
                </FormRow>
                <FormRow>
                    <Select label="Type" items={dropDown}
                            onValueChanged={(e) => createQuestion.value.type = e.detail.value as QuestionType}/>
                </FormRow>
            </FormLayout>
        </Dialog>
    );
}

function QuestionTable(props: { questions: Question[] }) {
    const navigate = useNavigate();
    const showQuestion = ({item}: { item: Question }) => {
        return <ViewDetailButton onClick={() => navigate(`/question/${item.id}`)}/>;
    };
    return (
        <Grid key={"id"} items={props.questions} className={styles.riddler_table} allRowsVisible={true}>
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