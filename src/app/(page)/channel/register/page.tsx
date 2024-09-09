"use client";
import React, { useState, ChangeEvent, FormEvent } from 'react';
import { useRouter } from 'next/navigation';
import { insertChannel } from '@/app/service/channel/channel.api';


export default function RegisterChannel() {
    const [channel, setChannel] = useState<ChannelModel>({ id: '', name: '', participants: [] });
    const router = useRouter();

    // 입력값 변경 핸들러
    const handleChange = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { id, value } = event.target;
        setChannel(prevChannel => ({ ...prevChannel, [id]: value }));
    };

    // 참가자 입력값 변경 핸들러
    const handleParticipantsChange = (event: ChangeEvent<HTMLTextAreaElement>) => {
        const value = event.target.value;
        const participantsArray = value.split(',').map(participant => participant.trim());
        setChannel(prevChannel => ({ ...prevChannel, participants: participantsArray }));
    };

    // 폼 제출 핸들러
    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        try {
            console.log('제출된 채널:', channel);
            
            const result = await insertChannel(channel); // insertChannel 함수 호출
            if (result) {
                alert('채널이 성공적으로 저장되었습니다.');
                router.push('/');
            } else {
                alert('채널 저장에 실패하였습니다.');
            }
        } catch (error) {
            console.error('오류가 발생했습니다:', error);
            alert('채널 저장 중 오류가 발생했습니다.');
        }
    };

    return (
        <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
            <div className="w-full max-w-md bg-white shadow-lg rounded-lg p-6">
                <h1 className="text-2xl font-bold mb-6 text-center">채널 등록</h1>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <label htmlFor="name" className="block text-sm font-medium text-gray-700">채널 이름:</label>
                        <input
                            type="text"
                            id="name"
                            value={channel.name}
                            onChange={handleChange}
                            required
                            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                        />
                    </div>
                    <div>
                        <label htmlFor="participants" className="block text-sm font-medium text-gray-700">참가자 (쉼표로 구분):</label>
                        <textarea
                            id="participants"
                            onChange={handleParticipantsChange}
                            required
                            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                            rows={4}
                        />
                    </div>
                    <button
                        type="submit"
                        className="w-full py-2 px-4 bg-blue-500 text-white font-semibold rounded-md shadow-sm hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-300"
                    >
                        제출
                    </button>
                </form>
            </div>
        </main>
    );
}
