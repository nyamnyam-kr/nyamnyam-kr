'use client'

import React, { useEffect, useState } from 'react';
import { useModalWishlistContext } from '../context/ModalWishlistContext';
import * as Icon from "@phosphor-icons/react/dist/ssr";
import Link from 'next/link'; 

interface ModalWishlistDetailProps {
    userId: number;
    wishlistId: number;
    wishlistName: string;
    closeDetails: () => void; 
}

const ModalWishlistDetail: React.FC<ModalWishlistDetailProps> = ({ userId, wishlistId, wishlistName, closeDetails }) => {
    const { isModalOpen } = useModalWishlistContext();
    const [restaurants, setRestaurants] = useState<RestaurantModel[]>([]);

    useEffect(() => {
        const fetchRestaurants = async () => {
            const response = await fetch(`http://localhost:8080/api/wishList/restaurants?wishListId=${wishlistId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'userId': userId.toString(),
                },
            });

            if (response.ok) {
                const restaurantData = await response.json();
                setRestaurants(restaurantData);
            } else {
                const errorText = await response.text();
                console.error(errorText);
            }
        };

        if (isModalOpen && wishlistId) {
            fetchRestaurants();
        }
    }, [isModalOpen, userId, wishlistId]);

    if (!isModalOpen) return null;

    return (
        <>
            <div className={`modal-wishlist-overlay`} onClick={closeDetails}></div>
            <div className={`modal-wishlist-block`}>
                <div className={`modal-wishlist-main`} onClick={(e) => { e.stopPropagation() }}>
                    <div className="heading flex items-center justify-between p-4">
                        <div className="heading5 text-center" style={{ fontSize: '24px', fontWeight: 'bold' }}>{wishlistName}</div>
                        <div className="close-btn cursor-pointer" onClick={closeDetails}>
                            <Icon.X size={24} />
                        </div>
                    </div>
                    <div className="list-product p-4 flex justify-center flex-wrap">
                        {restaurants.map((restaurant, index) => (
                            <div
                                key={index}
                                className='item flex flex-col items-center justify-center border-b cursor-pointer'
                                style={{
                                    width: '200px',
                                    height: '200px',
                                    borderRadius: '8px',
                                    padding: '10px',
                                    margin: '5px',
                                    textAlign: 'center'
                                }}
                            >
                                <Link href={`/restaurant/${restaurant.id}`} passHref>
                                    <div onClick={closeDetails} style={{ textDecoration: 'none', color: 'black', width: '100%', height: '100%' }}>
                                        <img src={restaurant.thumbnailImageUrl} alt={restaurant.name} style={{ borderRadius: '8px', width: '100%', height: 'auto' }} />
                                        <div
                                            className="name text-button"
                                            style={{
                                                fontSize: '18px',
                                                fontWeight: '300',
                                                color: 'black',
                                            }}
                                        >
                                            {restaurant.name}
                                        </div>
                                    </div>
                                </Link>
                            </div>
                        ))}
                    </div>
                    <div className="footer-modal p-4 border-t text-center">
                        <div onClick={closeDetails} className="text-button mt-4 cursor-pointer">Or continue</div>
                    </div>
                </div>
            </div>

            <style jsx>{`
                .modal-wishlist-overlay {
                    position: fixed;
                    top: 0;
                    left: 0;
                    right: 0;
                    bottom: 0;
                    background: rgba(0, 0, 0, 0.5);
                    z-index: 999;
                }

                .modal-wishlist-block {
                    position: fixed;
                    top: 0;
                    right: 0;
                    width: 400px;
                    height: 100%;
                    background: white;
                    box-shadow: -2px 0 5px rgba(0, 0, 0, 0.3);
                    z-index: 1000;
                }

                .modal-wishlist-main {
                    height: 100%;
                    overflow-y: auto;
                }

                .close-btn {
                    padding: 10px;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                }
            `}</style>
        </>
    );
};

export default ModalWishlistDetail;
