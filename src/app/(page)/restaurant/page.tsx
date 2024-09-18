"use client"

import axios from "axios";
import { useEffect, useState } from "react";
import Star from "../star/page";
import { useRouter } from "next/navigation";

export default function Restaurant({ restaurantId }: { restaurantId: number }) {
    const [allAverage, setAllAverage] = useState<number | null>(null);
    const [tags, setTags] = useState<string[]>([]);
    const router = useRouter();

    useEffect(() => {
        const restaurantId = 1; // 테스트로 1 값 설정 
        const fetchAllAverage = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/posts/${restaurantId}/allAverage`)
                setAllAverage(response.data);
            } catch (error) {
                console.error('전체 평점 오류: ', error);
            }
        };
        const fetchTagRestaurant = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/tags/top5/${restaurantId}`)
                setTags(response.data);
            } catch (error) {
                console.error('top5 태그 불러오기 오류: ', error);
            }
        }
        fetchAllAverage();
        fetchTagRestaurant();
    }, [restaurantId])

    const handlePost = () => {
        router.push('/')
    }

    return (
        <div>
            <div className="mb-4">
                <strong>[레스토랑 전체 평점]</strong>
                {allAverage !== null ? (
                    <div className="flex items-center">
                        <Star w="w-6" h="h-6" readonly={true} rate={allAverage} onChange={() => { }} />
                        <p className="ml-2">{allAverage.toFixed(1)} / 5</p>
                    </div>
                ) : (
                    '등록된 평점이 없습니다.'
                )}
            </div>

            <div className="mb-4">
                <strong>[가장 많이 선택된 태그]</strong>
                {tags.length > 0 ? (
                    <ul>
                        {tags.map((tag, index) => (
                            <li key={index}>{tag}</li>
                        ))}
                    </ul>
                ) : (
                    <p>등록된 태그가 없습니다.</p>
                )}
            </div>
            <div>
                <button className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded"
                    onClick={handlePost}>
                    후기 보기
                </button>
            </div>
        </div>
    );
}