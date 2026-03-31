const BASE_URL = "/api/hikSystem";

export const fetchZones = async (filter = 'all') => {

        const endpoints = {
            'all': 'listAllZones',
            'armed': 'listArmedZones',
            'disarmed': 'listDisarmedZones',
            'bypassed': 'listBypassedZones',
            'alarming': 'listAlarmingZones',
        };
        const endpoint = endpoints[filter] || endpoints['all'];

        try {
            const response = await fetch(`${BASE_URL}/${endpoint}`, {
                method: 'GET',
                headers: {'Content-Type': 'application/json'},
                credentials: 'include'
            });
            if (!response.ok) {
                throw new Error(`Failed to fetch zones with filter: ${filter}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error fetching zones with filter ${filter}:`, error);
            return [];
    }
};
export const fetchPartitions = async () => {
            try {
                const response = await fetch(`${BASE_URL}/listPartitions`, {
                     method: 'GET',
                     headers: {'Content-Type': 'application/json'},
                      credentials: 'include'
                 });
                 if (!response.ok) {
                    throw new Error('Failed to fetch partitions');
                }
                return await response.json();
            } catch (error) {
                console.error("Error fetching partitions:", error);
                return [];
    }
};



export const sendZoneCommand = async (command, zoneId) => {
    try {
        const response = await fetch(`${BASE_URL}/${command}/${zoneId}`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            credentials: 'include'
        });
        if(!response.ok) {
            throw new Error(`Failed to execute zone command: ${command}`);
        } 
        return await response.json();
    } catch (error) {
        console.error(`Error executing command ${command}:`, error);
        return {status: "Error", message: error.message};
    }
};
export const sendPartitionCommand = async (command, partitionId) => {
     try {
        const response = await fetch(`${BASE_URL}/${command}/${partitionId}`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            credentials: 'include'
        });
        if(!response.ok) {
            throw new Error(`Failed to execute partition command: ${command}`);
        } 
        return await response.json();
    } catch (error) {
        console.error(`Error executing command ${command}:`, error);
        return {status: "Error", message: error.message};
    }
};
