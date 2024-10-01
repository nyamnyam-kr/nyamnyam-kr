'use client'

import React, { useEffect, useState } from 'react'
import Link from 'next/link'
import { useModalWishlistContext } from '../context/ModalWishlistContext'
import { useWishlist } from '../context/WishlistContext'
import * as Icon from "@phosphor-icons/react/dist/ssr";


const pastelColors = [
    '#FFB3BA', '#FFDFBA', '#FFABAB', '#FFC3A0', '#FF677D',
    '#D4A5A5', '#392F5A', '#F7B7A3', '#D5AAFF', '#A9DEF9'
];

const ModalWishlist = () => {
    const { isModalOpen, closeModalWishlist } = useModalWishlistContext();
    const { removeFromWishlist } = useWishlist();
    const [wishlistItems, setWishlistItems] = useState<string[]>([]);

    useEffect(() => {
        const fetchWishlist = async () => {
            const userId = '4';
            const response = await fetch(`http://localhost:8080/api/wishList`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'userId': userId.toString(),
                },
            });

            if (response.ok) {
                const data: WishListModel[] = await response.json();
                const names = data.map(item => item.name);
                setWishlistItems(names);
            }
        };

        if (isModalOpen) {
            fetchWishlist();
        }
    }, [isModalOpen]);

    if (!isModalOpen) return null;

    return (
        <>
            <div className={`modal-wishlist-overlay`} onClick={closeModalWishlist}></div>
            <div className={`modal-wishlist-block`}>
                <div className={`modal-wishlist-main`} onClick={(e) => { e.stopPropagation() }}>
                    <div className="heading flex items-center justify-between p-4">
                        <div className="heading5 ">Wishlist</div>
                        <div className="close-btn cursor-pointer" onClick={closeModalWishlist}>
                            <Icon.X size={24} />
                        </div>
                    </div>
                    <div className="list-product p-4 flex justify-center flex-wrap">
                        {wishlistItems.map((wishName, index) => (
                            <div
                                key={index}
                                className='item flex flex-col items-center justify-center border-b'
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
                                <div className="product-info" style={{ flexGrow: 1, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                                    <div className="name text-button" style={{ fontSize: '22px', fontWeight: 'bold' }}>{wishName}</div>
                                </div>

                                <div className="remove-wishlist-btn text-red underline cursor-pointer" style={{ fontSize: '14px' }} onClick={() => removeFromWishlist(wishName)}>
                                    Remove
                                </div>
                            </div>
                        ))}
                    </div>
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
    )
}

export default ModalWishlist;
