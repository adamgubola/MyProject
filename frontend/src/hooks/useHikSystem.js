import { useState, useEffect, useCallback } from "react";
import { fetchZones, fetchPartitions, sendZoneCommand, sendPartitionCommand } from "../api/alarmApi";

export const useHikSystem = (initialFilter = 'all') => {
    const [zones, setZones] = useState([]);
    const [partitions, setPartitions] = useState([]);
    const [filter, setFilter] = useState(initialFilter);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const refreshSystem = useCallback(async () => {
        setLoading(true);
        try {
            const [zonesData, partitinsData] = await Promise.all(
                [fetchZones(filter),
                 fetchPartitions()
                ]);
            setZones(zonesData);
            setPartitions(partitinsData)
            setError(null);
        }catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    }, [filter]);

    useEffect(() => {
        refreshSystem();
    }, [refreshSystem]);

    const executeZoneCommand = async (command, zoneId) => {
        setLoading(true);
        try {
            const result = await sendZoneCommand(command, zoneId);
            if(result && result.status !== "Error") {
                await refreshSystem();
            }else{
                setError(result.message || "Unknown error executing command");
            }
        }
        catch (err) {
            setError("Error executing command: " + err.message);
            return false;
        }finally {
            setLoading(false);
        }
    };
        const executePartitionCommand = async (command, partitionId) => {
        setLoading(true);
        try {
            const result = await sendPartitionCommand(command, partitionId);
            if(result && result.status !== "Error") {
                await refreshSystem();
            }else{
                setError(result.message || "Unknown error executing partition command");
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
        partitions,
        filter,
        error,
        loading,
        setFilter,
        refreshSystem,
        executeZoneCommand,
        executePartitionCommand
    };
};