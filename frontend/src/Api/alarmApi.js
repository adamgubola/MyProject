const BASE_URL = "/api/hikSystem";

export const fetchZones = async (filter = 'all') => {
        const endpoints = {
            'all': 'listAllZones',
            'armed': 'listArmedZones',
            'disarmed': 'listDisarmedZones',
            'bypassed': 'listBypassedZones',
            'alarming': 'listAlarmingZones'
        };
        const endpoint = endpoints[filter] || endpoints['all'];

        try {
            const response = await fetch(`${BASE_URL}/${endpoint}`);
            if (!response.ok) {
                throw new Error(`Failed to fetch zones with filter: ${filter}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error fetching zones with filter ${filter}:`, error);
            return [];
    }
};
export const sendCommand = async (command, zoneId) => {
    try {
        const response = await fetch(`${BASE_URL}/${command}/${zoneId}`, {
            method: "POST",
        });
        if(!response.ok) {
            throw new Error(`Failed to execute command: ${command}`);
        } 
        return await response.json();
    } catch (error) {
        console.error(`Error executing command ${command}:`, error);
        return {status: "Error", message: error.message};
    }
};
