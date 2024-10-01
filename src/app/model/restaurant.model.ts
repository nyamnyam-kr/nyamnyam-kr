interface RestaurantModel {
    id?: number;
    name?: string;
    type?: string;
    address?: string;
    tel?: string;
    rate?: number;
    thumbnailImageUrl?: string;
    subImageUrl?: string;
    menu: string; 
    operation: string;  
    lat?: number;
    lng?: number;
}