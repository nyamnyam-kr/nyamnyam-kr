interface ChannelModel {
    id?: string; // Channel ID (optional for update, auto-generated for new records)
    name: string; // Channel name
    participants?: string[]; // List of participant IDs
}

