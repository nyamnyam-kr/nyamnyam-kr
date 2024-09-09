
export async function insertChannel(channel: ChannelModel): Promise<any | {status: number}> {
    try {
        const ChannelModel = {
            name: channel.name
        }
        const response = await fetch('http://localhost:8080/api/channels', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(ChannelModel)
        });
        const data: any = await response.json();
        return data;

    } catch (e) {
        console.log('There has been a problem with your fetch operation', e);
        return {status: 500 };
    }
}