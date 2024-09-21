"use client";

import { insertReply } from "@/app/service/reply/reply.api";
import { useParams, useRouter } from "next/navigation";
import { ChangeEvent, FormEvent, useEffect, useState } from "react";

export default function ReplyRegister() {
    const router = useRouter();
    const { restaurantId, id } = useParams();
    const [formData, setFormData] = useState<ReplyModel>({
        content: '',
        postId: Number(id),
        userId: 1 // 강제값으로 수정 필요!! 
    });
    useEffect(() => {
        if (id) {
            setFormData((prev) => ({
                ...prev,
                postId: Number(id)
            }));
        }
    }, [id]);

    const handleChange = (
        e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
    ) => {
        const { name, value } = e.target as HTMLInputElement;
        setFormData({
            ...formData,
            [name]: value,
        });
    }

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();
        const {content, postId, userId} = formData;
        const replyData = {content, postId, userId};

        console.log("db전송 데이터: ", replyData);

        await insertReply(replyData);
        router.push(`/post/${restaurantId}/${postId}/reply`)
    };

    return (
        <main className="flex min-h-screen flex-col items-center">
            <h1>[댓글 작성]</h1>
            <form onSubmit={handleSubmit} className="space-y-4 p-4">
                <div>
                    <label>내용</label>
                    <input
                        type="text"
                        name="content"
                        value={formData.content}
                        onChange={handleChange}
                        className="border rounded p-2 w-full"
                    />
                </div>
                <button type="submit" className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
                    등록하기
                </button>
            </form>
        </main>
    );
}