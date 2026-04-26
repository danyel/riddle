import {useEffect, useState} from "react";
import Answer from "Frontend/generated/be/riddler/domain/answer/api/Answer";
import {Grid, GridColumn} from "@vaadin/react-components";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import {AnswerService} from "Frontend/generated/endpoints";

export interface AnswersTableProperties {
    questionId: string;
}

export default function AnswersTable(props: AnswersTableProperties) {
    const [answers, setAnswers] = useState<Answer[]>([]);
    useEffect(() => {
        AnswerService.findByQuestion(props.questionId)
            .then(setAnswers);
    }, [props.questionId]);

    return (
        <Grid key={"id"} items={answers} className={styles.question_table} allRowsVisible={true}>
            <GridColumn key={"id"} path={"id"}/>
            <GridColumn key={"value"} path={"value"}/>
        </Grid>
    );
}