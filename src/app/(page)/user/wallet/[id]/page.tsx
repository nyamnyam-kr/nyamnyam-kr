"use client";
import React, { useEffect, useState } from "react";
import { useParams } from "next/navigation";

export default function Wallet() {
    const [wallet, setWallet] = useState<ReceiptModel[]>([]);
    const [selectedIds, setSelectedIds] = useState<Set<number>>(new Set());
    const { id } = useParams();

    useEffect(() => {
        fetch(`http://localhost:8080/api/receipt/wallet/${id}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to fetch group details");
                }
                return response.json();
            })
            .then((data) => {
                const updatedData = data.map((item: ReceiptModel) => ({
                    ...item,
                    date: item.date ? item.date.slice(0, 11) : '',
                }));
                setWallet(updatedData);
            });
    }, [id]);

    const totalExpenditure = wallet.reduce((sum, item) => sum + item.price, 0);

    const handleCheckboxChange = (id: number) => {
        const newSelectedIds = new Set(selectedIds);
        if (newSelectedIds.has(id)) {
            newSelectedIds.delete(id);
        } else {
            newSelectedIds.add(id);
        }
        setSelectedIds(newSelectedIds);
    };

    const handleDeleteConfirmation = () => {
        if (selectedIds.size === 0) {
            alert("삭제할 항목을 선택해 주세요.");
            return;
        }

        const confirmed = window.confirm("선택한 항목을 삭제하시겠습니까?");
        if (confirmed) {
            const idsToDelete = Array.from(selectedIds);

            const deletePromises = idsToDelete.map((receiptId) =>
                fetch(`http://localhost:8080/api/receipt/${receiptId}`, {
                    method: 'DELETE', // DELETE 메서드 사용
                })
            );

            Promise.all(deletePromises)
                .then(() => {
                    console.log("삭제된 ID:", idsToDelete);
                    // 상태 업데이트
                    const updatedWallet = wallet.filter(item => !selectedIds.has(item.id));
                    setWallet(updatedWallet);
                    setSelectedIds(new Set());
                })
                .catch((error) => {
                    console.error("삭제 중 오류 발생:", error);
                });
        } else {
            console.log("삭제가 취소되었습니다.");
        }
    };

    return (
        <main className="flex min-h-screen flex-col items-center p-6 bg-gray-100">
            <h1>내 지출 내역</h1>
            <div className="w-full max-w-4xl bg-white shadow-lg rounded-lg p-6">
                <table className="w-full border-collapse bg-white shadow-md rounded-lg overflow-hidden">
                    <thead>
                    <tr className="bg-blue-600 text-white">
                        <th className="py-3 px-4 border-b"></th>
                        <th className="py-3 px-4 border-b">음식점 이름</th>
                        <th className="py-3 px-4 border-b">이용날짜</th>
                        <th className="py-3 px-4 border-b">지출</th>
                    </tr>
                    </thead>
                    <tbody>
                    {wallet.map((w) => (
                        <tr key={w.id} className="text-black">
                            <td className="py-3 px-4 border-b">
                                <input
                                    type="checkbox"
                                    checked={selectedIds.has(w.id)}
                                    onChange={() => handleCheckboxChange(w.id)}
                                />
                            </td>
                            <td className="py-3 px-4 border-b">{w.name}</td>
                            <td className="py-3 px-4 border-b">{w.date}</td>
                            <td className="py-3 px-4 border-b">{w.price}</td>
                        </tr>
                    ))}
                    <tr className="bg-blue-600 text-white">
                        <td className="py-3 px-4 border-b" colSpan={2}>합계</td>
                        <td className="py-3 px-4 border-b">{totalExpenditure}</td>
                    </tr>
                    </tbody>
                </table>
                <button
                    onClick={handleDeleteConfirmation}
                    className="mt-4 bg-red-600 text-white px-4 py-2 rounded"
                >
                    삭제
                </button>
            </div>
        </main>
    );
}
