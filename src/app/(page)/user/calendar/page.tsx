"use client";
import React, { useEffect, useState } from 'react';
import {format, addMonths, subMonths, isSameDay, isToday} from 'date-fns';
import { HiOutlineArrowCircleLeft, HiOutlineArrowCircleRight } from 'react-icons/hi';

interface ReceiptModel {
    id: number;
    name: string;
    price: number;
    date: string;
}

const Calendar: React.FC = () => {
    const [currentMonth, setCurrentMonth] = useState<Date>(new Date());
    const [selectedDate, setSelectedDate] = useState<Date | null>(null);
    const [inputValue, setInputValue] = useState<string>('');
    const [wallet, setWallet] = useState<ReceiptModel[]>([]);
    const [totalExpenditure, setTotalExpenditure] = useState<number>(0);
    const [selectedIds, setSelectedIds] = useState<Set<number>>(new Set());

    const preMonth = () => {
        setCurrentMonth(subMonths(currentMonth, 1));
    };

    const nextMonth = () => {
        setCurrentMonth(addMonths(currentMonth, 1));
    };

    const goToday = () => {
        setCurrentMonth(new Date());
    };

    const handleDateClick = (date: Date) => {
        setSelectedDate(date);
        setInputValue(''); // 날짜 선택 시 입력란 초기화
    };

    const handleInputChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        setInputValue(e.target.value);
    };

    const saveInput = () => {
        console.log(`Saved for ${format(selectedDate!, 'yyyy-MM-dd')}: ${inputValue}`);
        setSelectedDate(null); // 입력 후 선택 초기화
    };

    const fetchWalletData = async (date: Date) => {
        const formattedDate = format(date, 'yyyy-MM-dd');
        const response = await fetch(`http://localhost:8080/api/receipt/wallet/${formattedDate}`);
        if (response.ok) {
            const data = await response.json();
            const updatedData = data.map((item: ReceiptModel) => ({
                ...item,
                date: item.date ? item.date.slice(0, 11) : '',
            }));
            setWallet(updatedData);
            setTotalExpenditure(updatedData.reduce((sum: number, item: ReceiptModel) => sum + item.price, 0));
        }
    };

    useEffect(() => {
        if (selectedDate) {
            fetchWalletData(selectedDate);
        }
    }, [selectedDate]);

    const handleCheckboxChange = (id: number) => {
        const newSelectedIds = new Set(selectedIds);
        if (newSelectedIds.has(id)) {
            newSelectedIds.delete(id);
        } else {
            newSelectedIds.add(id);
        }
        setSelectedIds(newSelectedIds);
    };

    const handleDeleteConfirmation = async () => {
        if (selectedIds.size === 0) {
            alert("삭제할 항목을 선택해 주세요.");
            return;
        }

        const confirmed = window.confirm("선택한 항목을 삭제하시겠습니까?");
        if (confirmed) {
            const idsToDelete = Array.from(selectedIds);
            const deletePromises = idsToDelete.map((receiptId) =>
                fetch(`http://localhost:8080/api/receipt/${receiptId}`, {
                    method: 'DELETE',
                })
            );

            await Promise.all(deletePromises);
            setWallet(wallet.filter(item => !selectedIds.has(item.id)));
            setSelectedIds(new Set());
        } else {
            console.log("삭제가 취소되었습니다.");
        }
    };

    const renderDaysInMonth = () => {
        const daysInMonth = new Date(currentMonth.getFullYear(), currentMonth.getMonth() + 1, 0).getDate();
        const firstDay = new Date(currentMonth.getFullYear(), currentMonth.getMonth(), 1).getDay();

        const days: JSX.Element[] = [];
        for (let i = 0; i < firstDay; i++) {
            days.push(<div key={`empty-${i}`} className="w-12 h-12"></div>);
        }
        for (let day = 1; day <= daysInMonth; day++) {
            const date = new Date(currentMonth.getFullYear(), currentMonth.getMonth(), day);
            const isTodayDate = isToday(date);
            days.push(
                <div
                    key={day}
                    className={`w-12 h-12 flex items-center justify-center cursor-pointer ${isSameDay(date, selectedDate!) ? 'bg-blue-500' : ''} ${isTodayDate ? 'relative' : ''}`}
                    onClick={() => handleDateClick(date)}
                >
                    {isTodayDate && (
                        <div className="absolute w-8 h-8 rounded-full border border-blue-600 flex items-center justify-center">
                            {day}
                        </div>
                    )}
                    {!isTodayDate && <span>{day}</span>}
                </div>
            );
        }
        return days;
    };

    return (
        <div className="p-5">
            <CalendarHeader
                currentMonth={currentMonth}
                preMonth={preMonth}
                nextMonth={nextMonth}
                goToday={goToday}
            />
            <div className="grid grid-cols-7 gap-1 mt-5">
                {renderDaysInMonth()}
            </div>
            {selectedDate && (
                <div className="mt-5">
                    <h2 className="text-lg mb-2">{format(selectedDate, 'yyyy-MM-dd')}에 대한 입력:</h2>
                    <textarea
                        className="w-full p-2 border rounded"
                        value={inputValue}
                        onChange={handleInputChange}
                        rows={4}
                    />
                    <button className="mt-2 px-4 py-2 bg-blue-500 text-white rounded" onClick={saveInput}>
                        저장
                    </button>
                </div>
            )}
            {wallet.length > 0 && selectedDate && (
                <div className="mt-5">
                    <h2 className="text-lg mb-2">{format(selectedDate, 'yyyy-MM-dd')} 지출 내역:</h2>
                    <table className="w-full border-collapse bg-white shadow-md rounded-lg overflow-hidden">
                        <thead>
                        <tr className="bg-blue-600 text-white">
                            <th className="py-3 px-4 border-b">음식점 이름</th>
                            <th className="py-3 px-4 border-b">지출</th>
                        </tr>
                        </thead>
                        <tbody>
                        {wallet.map((w) => (
                            <tr key={w.id} className="text-black">
                                <td className="py-3 px-4 border-b">{w.name}</td>
                                <td className="py-3 px-4 border-b">{w.price}</td>
                            </tr>
                        ))}
                        <tr className="bg-blue-600 text-white">
                            <td className="py-3 px-4 border-b" colSpan={1}>합계</td>
                            <td className="py-3 px-4 border-b">{totalExpenditure}</td>
                        </tr>
                        </tbody>
                    </table>
                    <button onClick={handleDeleteConfirmation} className="mt-4 bg-red-600 text-white px-4 py-2 rounded">
                        삭제
                    </button>
                </div>
            )}
        </div>
    );
};

type CalendarHeaderProps = {
    currentMonth: Date;
    preMonth: () => void;
    nextMonth: () => void;
    goToday: () => void;
};

const CalendarHeader: React.FC<CalendarHeaderProps> = ({ currentMonth, preMonth, nextMonth, goToday }) => {
    return (
        <div className='flex items-center justify-between w-full gap-3 p-5 text-3xl font-semibold border rounded rounded-b-none'>
            <div className='gap-3'>
                <span className='mr-3'>{format(currentMonth, 'yyyy')}년</span>
                <span>{format(currentMonth, 'M')}월</span>
            </div>
            <div className='flex items-center gap-2 cursor-pointer'>
                <HiOutlineArrowCircleLeft onClick={preMonth} />
                <button className='text-lg' onClick={goToday}>오늘</button>
                <HiOutlineArrowCircleRight onClick={nextMonth} />
            </div>
        </div>
    );
};

export default Calendar;
