"use client"

import axios from "axios";
import {useParams} from "next/navigation";
import {useEffect} from "react";
import {ReceiptModel} from "src/app/model/receipt.model";

export default function userPost() {
    const { id } = useParams();



    useEffect(() => {
        const Count = async () => {
            try {
                const resp = await axios.get(`http://localhost:8080/api/posts/list/${id}`);
                if (resp.status === 200) {
                    console.log(resp.data);
                }
            } catch (error) {
                console.error("Error fetching count data", error);
            }
        };
        Count();
    }, []);


    return (
        <>
            <div>h1h1h1hh1</div>

            <div>h1h1h1hh1</div>
            <div>h1h1h1hh1</div>
            <div>h1h1h1hh1</div>
            <div>h1h1h1hh1</div>
            <div>h1h1h1hh1</div>
            <div>h1h1h1hh1</div>
        </>
    )
}