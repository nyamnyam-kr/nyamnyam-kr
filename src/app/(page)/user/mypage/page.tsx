"use client";
import React, {FormEvent, useState} from "react";
import {insertOpinion} from "@/app/service/user/user.api";

interface OpinionModel {
    id: number;
    userId: number;
    content: string;
    entryDate: string;
}

export default function MyPage() {
    const [content, setContent] = useState("");
    const userId = 1; // Replace this with the actual user ID
    const currentDate = new Date().toISOString(); // Adjust format if needed

    const submit = async (content: string) => {
        const report: OpinionModel = {
            id: Date.now(),
            userId: userId,
            content: content,
            entryDate: currentDate,
        };

        try {
            const result = await insertOpinion(report);
            if (result) {
                alert('의견이 성공적으로 제출되었습니다.');
                setContent(""); // Clear the textarea after submission
            } else {
                alert('의견 제출에 실패하였습니다.');
            }
        } catch (error) {
            console.error('오류가 발생했습니다:', error);
            alert('의견 제출 중 오류가 발생했습니다.');
        }
    }

    return (
        <div>
            <h2>냠냠에 전하고싶은 의견이 있나요?</h2>
            <h2>00님의 소중한 의견을 꼼꼼히 읽어볼게요</h2>
            <form onSubmit={(e) => {
                e.preventDefault();
                if (window.confirm("냠냠에 의견을 보낼까요?")) {
                    submit(content);
                }
            }}>
                <textarea
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                    placeholder="여기에 의견을 남겨주세요"
                    rows={4}
                    style={{width: '100%', marginBottom: '10px'}}
                />
                <button type="submit">제출</button>
            </form>
        </div>
    );
}
