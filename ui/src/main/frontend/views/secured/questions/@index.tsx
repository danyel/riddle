import {useEffect, useState} from 'react';
import {Grid, GridColumn} from "@vaadin/react-components";
// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';

// CRITICAL: You must import the iconset for the icons to render
import '@vaadin/icons';
import {useNavigate} from "react-router";
import Question from "Frontend/generated/be/riddler/v1/question/api/Question";
import {QuestionEndpoint} from "Frontend/generated/endpoints";
import {ViewDetailButton} from "Frontend/components/ui/button";

export default function QuestionsView() {
    const [questions, setQuestions] = useState<Question[]>([]);
    const navigate = useNavigate();
    useEffect(() => {
        QuestionEndpoint.getQuestions().then(setQuestions);
    }, []);

    const answerRenderer = ({item}: { item: Question }) => {
        return <ViewDetailButton onClick={() => navigate(`/question/${item.id}`)}/>;

    };

    return (
        <Grid key={"id"} items={questions} className={styles.question_table} allRowsVisible={true}>
            <GridColumn key={"question"} path={"question"}/>
            <GridColumn key={"type"} path={"type"}/>
            <GridColumn header={'Action'} renderer={answerRenderer}/>
        </Grid>
    );
}