"use client"
import Home from 'src/app/(page)/restaurant/page';
import './globals.css'
import StoreProvider from './StoreProvider';
import { SearchProvider, useSearchContext } from './components/SearchContext';
import { useEffect, useState } from 'react';
import Header from './components/common/Header';
import TabFeatures from './(page)/restaurant/page2';



const Page = () => {
    // const { setSearchTerm } = useSearchContext();

    // useEffect(() => {
    //     const params = new URLSearchParams(window.location.search);
    //     const term = params.get('search');
    //     if (term) {
    //         setSearchTerm(term);
    //     }
    // }, [setSearchTerm]);


    const { searchTerm } = useSearchContext(); // Context에서 검색어 가져오기
    const [showHome, setShowHome] = useState(false);


    useEffect(() => {
        if (searchTerm) {
            setShowHome(true); // 검색어가 있을 경우 Home 컴포넌트를 보여주는
        } else {
            setShowHome(false); // 검색어가 없으면 TabFeatures 보여주는
        }
    }, [searchTerm]);

    return (
        <StoreProvider>
            {showHome ? <Home /> : <TabFeatures data={[]} start={0} limit={5} />}
        </StoreProvider>
    );
};

export default Page;