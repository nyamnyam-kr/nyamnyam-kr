import React from 'react';

interface SearchBarProps {
    searchTerm: string;
    onSearch: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const SearchBar: React.FC<SearchBarProps> = ({ searchTerm, onSearch }) => {
    return (
        <input
            type="text"
            placeholder="Search by name, address, type, or menu..."
            value={searchTerm}
            onChange={onSearch}
            className="w-full px-4 py-2 border border-gray-300 rounded-md"
        />
    );
};

export default SearchBar;