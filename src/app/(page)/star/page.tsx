"use client";
import { useState } from "react";
import { FaStar } from "react-icons/fa6";

export default function Star({ w, h, readonly, rate, onChange }: StarModel) {
    const [rating, setRating] = useState(rate || 0);

    const handleClickStar = (index: number) => {
        if (!readonly) {
            setRating(index + 1);
            if (onChange) {
                onChange(index + 1);
            }
        }
    }

    return (
        <div className={`flex`}>
            {Array.from({ length: 5 }).map((_, index) => (
                <div
                    className={`relative ${w} ${h} cursor-pointer`}
                    key={index}
                >
                    <FaStar
                        onClick={() => handleClickStar(index)}
                        className={`${w} ${h} ${!readonly && rating >= index + 1 ? 'text-primary-review' : 'text-gray200'}`}
                    />
                </div>
            ))}
        </div>
    );
}