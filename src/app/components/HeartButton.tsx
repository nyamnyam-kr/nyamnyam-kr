import { useState } from 'react';
import Modal from './Modal'; // Modal 컴포넌트 임포트

interface HeartButtonProps {
    restaurantId: number; 
}

const HeartButton = ({ restaurantId }: HeartButtonProps) => {
    const [isFavorited, setIsFavorited] = useState(false);
    const [wishList, setWishList] = useState<WishListModel[]>([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const userId = 1; 

    const handleToggleFavorite = async (event: React.MouseEvent) => {
        event.stopPropagation(); // 상세보기로 이동하는 원래 기본 클릭 이벤트 방지
        setIsFavorited(!isFavorited);

        // 위시리스트 가져오기
        try {
            const response = await fetch(`http://localhost:8080/api/wishList`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'userId': userId.toString(), 
                },
            });

            if (!response.ok) {
                throw new Error('위시리스트를 불러오는 데 실패했습니다.');
            }

            const wishListData = await response.json();
            setWishList(wishListData);
            setIsModalOpen(true); // 모달 열기
        } catch (error) {
            console.error('Error fetching wish list:', error);
        }
    };

    return (
        <>
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

            <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)}>
                <h2 className="text-2xl font-bold mb-4">내 위시리스트</h2>
                {wishList.length === 0 ? (
                    <p>위시리스트에 항목이 없습니다.</p>
                ) : (
                    <ul className="space-y-4">
                        {wishList.map((item) => (
                            <li key={item.id} className="flex justify-between items-center p-4 border rounded-lg shadow-sm">
                                <div>
                                    <h3 className="text-lg font-semibold">{item.name}</h3>
                        
                                </div>
                            </li>
                        ))}
                    </ul>
                )}
            </Modal>
        </>
    );
};

export default HeartButton;
