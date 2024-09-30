"use client";
import React, { useState, useEffect } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import { EventContentArg } from "@fullcalendar/core";
import { Dropdown } from "react-bootstrap";
import { ReceiptModel } from "src/app/model/receipt.model";
import { useParams } from "next/navigation";

interface Todo {
    todo: string[];
}

interface CalendarEvent {
    title: string;
    date: string;
    color?: string;
    extendedProps?: Todo; // extendedProps 타입을 Todo로 변경
}

const MyCalendar: React.FC = () => {
    const [openDropdowns, setOpenDropdowns] = useState<{ [key: string]: boolean }>({});
    const [wallet, setWallet] = useState<ReceiptModel[]>([]);
    const { id } = useParams();

    const totalExpenditure = wallet.reduce((sum, item) => sum + item.price, 0);

    const handleToggle = (eventId: string) => {
        setOpenDropdowns(prevState => ({
            ...prevState,
            [eventId]: !prevState[eventId]
        }));
    };

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

    const events: CalendarEvent[] = wallet.map((item) => ({
        title: item.name,
        date: item.date,
        color: '#e4693b',
        extendedProps: {
            todo: [`지출: ${item.price}`] // 예시로 가격을 todo 배열로 추가
        }
    }));

    const renderEventContent = (eventInfo: EventContentArg) => {
        const todos = eventInfo.event.extendedProps?.todo || [];
        const eventId = eventInfo.event.title; // 고유 ID 또는 제목 사용

        return (
            <Dropdown show={openDropdowns[eventId]} onToggle={() => handleToggle(eventId)}>
                <Dropdown.Toggle onClick={() => handleToggle(eventId)}>
                    <span>{eventInfo.event.title}</span>
                </Dropdown.Toggle>
                <Dropdown.Menu>
                    {todos.map((todoItem: string, index: number) => (
                        <Dropdown.Item key={index}>{todoItem}</Dropdown.Item>
                    ))}
                </Dropdown.Menu>
            </Dropdown>
        );
    };

    return (
        <div className="mt-20">
            <FullCalendar
                plugins={[dayGridPlugin]}
                initialView="dayGridMonth"
                events={events}
                eventContent={renderEventContent}
                editable={true}
                droppable={true}
            />
            <div className="bg-blue-600 text-white">
                <div className="py-3 px-4 border-b">지출합계 : {totalExpenditure}</div>
            </div>
        </div>
    );
};

export default MyCalendar;
