"use client"
import Home from 'src/app/(page)/restaurant/page';
import './globals.css'
import '../styles/'
import StoreProvider from './StoreProvider';
import { SearchProvider, useSearchContext } from './components/SearchContext';
import { useEffect } from 'react';
import Header from './components/common/Header';



const Page = () => {
    // const { setSearchTerm } = useSearchContext();

    // useEffect(() => {
    //     const params = new URLSearchParams(window.location.search);
    //     const term = params.get('search');
    //     if (term) {
    //         setSearchTerm(term);
    //     }
    // }, [setSearchTerm]);


    
    return (
        <StoreProvider>
            <Home />
        </StoreProvider>
    );
};

export default Page;