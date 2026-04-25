import {useEffect, useState} from 'react';
import {QuestionService} from "Frontend/generated/endpoints";
import Question from "Frontend/generated/be/riddler/question/bff/Question";
import {Answers} from "Frontend/components/Answer";
import {ViewConfig} from '@vaadin/hilla-file-router/types.js';
import {Grid, GridColumn} from "@vaadin/react-components";
// @ts-ignore
import styles from './questions.module.css';

export default function Index() {
    const [questions, setQuestions] = useState<Question[]>([]);

    useEffect(() => {
        QuestionService.getQuestions().then(setQuestions);
    }, []);

    const answerRenderer = ({item}: { item: Question }) => {
        return <Answers id={item.id}/>;
    };

    return (
        <Grid key={"id"} items={questions} className={styles.question_table} allRowsVisible={true}>
            <GridColumn key={"id"} path={"id"}/>
            <GridColumn key={"question"} path={"question"}/>
            <GridColumn header={'answer'} renderer={answerRenderer}/>
        </Grid>
    );
}


export const config: ViewConfig = {title: 'Questions'};