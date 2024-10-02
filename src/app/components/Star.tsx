"use client";
import { useEffect, useState } from "react";

export default function Star({ w, h, readonly, rate, onChange }: StarModel) {
    const [rating, setRating] = useState(rate || 0);

    useEffect(() => {
        if (typeof rate === "number") {
            setRating(rate);
        }
    }, [rate]);

    const handleClickStar = (index: number) => {
        if (!readonly) {
            const newRating = index + 1;
            if (rating === newRating) {
                setRating(0);
                onChange && onChange(0);
            } else {
                setRating(newRating);
                onChange && onChange(newRating);
            }
        }
    };

    // 조건문 위치 변경
    const calculateRate = (rate: number, index: number):number => {
        if (rate >= index) {
            return 100;
        } else if (rate > index - 1) {
            const decimal = rate - Math.floor(rate);
            return decimal * 100;
        } else {
            return 0;
        }
    };

    return (
        <div className={`flex`}>
            {Array.from({ length: 5 }).map((_, index) => (
                <div
                    className={`relative ${w} ${h} cursor-pointer`}
                    key={index}
                    style={{ position: 'relative', display: 'inline-block' }}
                    onClick={() => handleClickStar(index)}
                >
                    <svg
                        className={`${w} ${h}`}
                        viewBox="0 0 24 24"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                        style={{ display: 'block' }}
                    >
                        <polygon
                            points="12,2 15,10 22,10 17,14 19,22 12,17 5,22 7,14 2,10 9,10"
                            fill="gray"
                        />
                        <polygon
                            points="12,2 15,10 22,10 17,14 19,22 12,17 5,22 7,14 2,10 9,10"
                            fill="#FFD700"
                            clipPath={`inset(0 ${100 - calculateRate(rating, index + 1)}% 0 0)`}
                        />
                    </svg>
                </div>
            ))}
        </div>
    );
}