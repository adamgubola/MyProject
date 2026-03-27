import { useState, useEffect, useCallback } from "react";
import { fetchZones, sendCommand } from "../api/alarmApi";

export const useHikSystem = (initialFilter = 'all') => {
    const [zones, setZones] = useState([]);
    const [filter, setFilter] = useState(initialFilter);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const refreshZones = useCallback(async () => {
        setLoading(true);
        try {
            const data = await fetchZones(filter);
            setZones(data);
            setError(null);
        }catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    }, [filter]);

    useEffect(() => {
        refreshZones();
    }, [refreshZones]);

    const executeCommand = async (command, zoneId) => {
        setLoading(true);
        try {
            const result = await sendCommand(command, zoneId);
            if(result && result.status !== "Error") {
                await refreshZones();
                return true;
            }else{
                setError(result.message || "Unknown error executing command");
                return false;
            }
        }
        catch (err) {
            setError("Error executing command: " + err.message);
            return false;
        }finally {
            setLoading(false);
        }
    };
    return {
        zones,
        filter,
        error,
        loading,
        setFilter,
        refreshZones,
        executeCommand
    };
};