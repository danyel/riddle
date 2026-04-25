import {useParams} from "react-router";

export default function GetQuestion() {
    const { id } = useParams();
    return (<div>ById {id}</div>);
}