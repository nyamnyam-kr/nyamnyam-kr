"use client";
import React, {useEffect, useState} from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import {EventContentArg} from "@fullcalendar/core";
import {Dropdown} from "react-bootstrap";
import {ReceiptModel} from "src/app/model/receipt.model";

interface Todo {
    todo: string[];
}

interface CalendarEvent {
    title: string;
    date: string;
    color?: string;
    extendedProps?: Todo;
}

const MyCalendar: React.FC = () => {
    const [openDropdowns, setOpenDropdowns] = useState<{ [key: string]: boolean }>({});
    const [wallet, setWallet] = useState<ReceiptModel[]>([]);
    const  id= 1;

    const [currentMonth, setCurrentMonth] = useState(new Date().getMonth() + 1);
    const [currentYear, setCurrentYear] = useState(new Date().getFullYear());

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
    }, []);

    const events: CalendarEvent[] = wallet.map((item) => ({
        title: item.name,
        date: item.date,
        color: '#e4693b',
        extendedProps: {
            todo: [`지출: ${item.price}`]
        }
    }));

    const renderEventContent = (eventInfo: EventContentArg) => {
        const todos = eventInfo.event.extendedProps?.todo || [];
        const eventId = eventInfo.event.title;

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

    const handleDateChange = (dateInfo: { start: Date; end: Date }) => {
        const month = dateInfo.start.getMonth() + 1; // 0부터 시작하므로 1을 더해줌
        const year = dateInfo.start.getFullYear();
        setCurrentMonth(month);
        setCurrentYear(year);
    };

    useEffect(() => {
        const today = new Date();
        setCurrentMonth(today.getMonth() + 1);
        setCurrentYear(today.getFullYear());
    }, []);



    const currentMonthEvents: CalendarEvent[] = events.filter(event => {
        const eventDate = new Date(event.date + 'T00:00:00+09:00');
        return eventDate.getMonth() + 1 === currentMonth && eventDate.getFullYear() === currentYear;
    });



    return (
        <div>
            <FullCalendar
                plugins={[dayGridPlugin]}
                initialView="dayGridMonth"
                events={currentMonthEvents}
                eventContent={renderEventContent}
                editable={true}
                droppable={true}
                datesSet={handleDateChange}
                fixedWeekCount={false}
            />
        </div>
    );
};

export default MyCalendar;
