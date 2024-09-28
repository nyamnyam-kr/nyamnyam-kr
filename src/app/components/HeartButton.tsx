import { useState } from 'react';

interface HeartButtonProps {
    restaurantId: any;
}

const HeartButton = ({ restaurantId }: HeartButtonProps) => {


    const [isFavorited, setIsFavorited] = useState(false);

    const handleToggleFavorite = (event: React.MouseEvent) => {
        event.stopPropagation(); // 상세보기로 이동하는 원래기본 클릭 이벤트 방지
        setIsFavorited(!isFavorited);
        console.log(`${restaurantId}가 ${isFavorited ? '제거' : '추가'}되었습니다.`);
    };

    return (
        <button onClick={handleToggleFavorite} className="focus:outline-none">
            {isFavorited ? (
                <svg xmlns="http://www.w3.org/2000/svg" className="h-8 w-8 text-white" fill="red" viewBox="0 0 24 24">
                    <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" />
                </svg>
            ) : (
                <svg xmlns="http://www.w3.org/2000/svg" className="h-8 w-8 text-white" fill="currentColor" viewBox="0 0 24 24">
                    <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" />
                </svg>
            )}
        </button>
    );
};

export default HeartButton
