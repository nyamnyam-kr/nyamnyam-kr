'use client'

import React, { useEffect, useState } from 'react';
import { useModalWishlistContext } from '../context/ModalWishlistContext';
import * as Icon from "@phosphor-icons/react/dist/ssr";
import ModalWishlistDetail from './ModalWishlistDetail';


const pastelColors = [
    '#FFB3BA', '#FFDFBA', '#FFABAB', '#FFC3A0', '#FF677D',
    '#D4A5A5', '#392F5A', '#F7B7A3', '#D5AAFF', '#A9DEF9'
];

const ModalWishlist = () => {
    const { isModalOpen, closeModalWishlist } = useModalWishlistContext();
    const [wishList, setWishList] = useState<WishListModel[]>([]);
    const [selectedWishlistId, setSelectedWishlistId] = useState<number | null>(null);
    const [selectedWishlistName, setSelectedWishlistName] = useState<string | null>(null);
    const [showDetail, setShowDetail] = useState(false); // 모달 전환 상태

    const userId = 1;

    useEffect(() => {
        const fetchWishlist = async () => {
            const response = await fetch(`http://localhost:8080/api/wishList`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'userId': userId.toString(),
                },
            });

            if (response.ok) {
                const wishListData = await response.json();
                setWishList(wishListData);
            }
        };

        if (isModalOpen) {
            fetchWishlist();
        }
    }, [isModalOpen]);

    const removeFromWishlist = async (wishlist: WishListModel) => {
        const response = await fetch(`http://localhost:8080/api/wishList/delete/wishList?id=${wishlist.id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'userId': userId.toString(),
            },
        });

        if (response.ok) {
            setWishList(prevItems => prevItems.filter(item => item.id !== wishlist.id));
        } else {
            console.error('Failed to delete item from wishlist');
        }
    };

    const openDetailsModal = (wishlistId: number, wishlistName: string) => {
        setSelectedWishlistId(wishlistId);
        setSelectedWishlistName(wishlistName);
        setShowDetail(true); // 디테일 모달 열기
    };

    const closeDetailsModal = () => {
        setShowDetail(false); // 디테일 모달 닫기
        setSelectedWishlistId(null); 
        setSelectedWishlistName(null); 
    };

    if (!isModalOpen) return null;

    return (
        <>
            <div className={`modal-wishlist-overlay`} onClick={closeModalWishlist}></div>
            <div className={`modal-wishlist-block`}>
                <div className={`modal-wishlist-main`} onClick={(e) => { e.stopPropagation() }}>
                    <div className="heading flex items-center justify-between p-4">
                        <div className="heading5 text-center" style={{ fontSize: '24px', fontWeight: 'bold' }}>Wishlist</div>
                        <div className="close-btn cursor-pointer" onClick={closeModalWishlist}>
                            <Icon.X size={24} />
                        </div>
                    </div>
                    {showDetail ? (
                        <ModalWishlistDetail
                            userId={userId}
                            wishlistId={selectedWishlistId!}
                            wishlistName={selectedWishlistName!}
                            closeDetails={closeDetailsModal} 
                        />
                    ) : (
                        <div className="list-product p-4 flex justify-center flex-wrap">
                            {wishList.map((wishlist, index) => (
                                <div
                                    key={index}
                                    className='item flex flex-col items-center justify-center border-b cursor-pointer'
                                    style={{
                                        backgroundColor: pastelColors[index % pastelColors.length],
                                        width: '200px',
                                        height: '200px',
                                        borderRadius: '8px',
                                        padding: '10px',
                                        margin: '5px',
                                        textAlign: 'center'
                                    }}
                                >
                                    <div className="product-info cursor-pointer" style={{ flexGrow: 1, display: 'flex', justifyContent: 'center', alignItems: 'center' }} onClick={() => openDetailsModal(wishlist.id, wishlist.name)}>
                                        <div className="name text-button" style={{ fontSize: '22px', fontWeight: 'bold' }}>{wishlist.name}</div>
                                    </div>
                                    <div className="remove-wishlist-btn text-red underline cursor-pointer" style={{ fontSize: '14px' }} onClick={() => removeFromWishlist(wishlist)}>
                                        Remove
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}
                    <div className="footer-modal p-4 border-t text-center">
                        <div onClick={closeModalWishlist} className="text-button mt-4 cursor-pointer">Or continue</div>
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

export default ModalWishlist;
