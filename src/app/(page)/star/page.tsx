"use client";
import { useEffect, useState } from "react";
import { FaStar } from "react-icons/fa6";

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

    const calculateRate = (rate: number, index: number) => {
        if (rate >= index) {
            return "100%";
        } else if (rate < index - 1) {
            return "0%";
        } else {
            const decimal = rate - Math.floor(rate);
            return `${decimal * 100}%`;
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
                    <FaStar className={`${w} ${h} text-gray-300`} style={{ position: 'absolute', top: 0, left: 0 }} />

                    <div
                        style={{
                            width: calculateRate(rating, index + 1), // 별의 채워질 비율만큼 너비 설정
                            overflow: "hidden",
                            position: "absolute", // 부모 컨테이너의 위치에 맞게 겹치도록 설정
                            top: 0,
                            left: 0,
                            height: "100%", // 전체 높이 차지
                            display: "flex",
                            alignItems: "center", // 세로 중앙 정렬
                        }}
                    >
                        <FaStar className={`${w} ${h} text-yellow-500`} />
                    </div>
                </div>
            ))}
        </div>
    );
}
