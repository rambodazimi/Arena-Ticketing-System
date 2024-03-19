CREATE PROCEDURE UpdateBraceletRequirement(IN ageRestrictionLimit INTEGER)
LANGUAGE SQL
BEGIN
    -- Update the BraceletRequired attribute in FloorTickets for tickets associated with events meeting the age restriction
    UPDATE FloorTickets AS ft
    SET BraceletRequired = TRUE
    WHERE EXISTS (
        SELECT 1
        FROM Tickets AS t
        JOIN Events AS e ON t.EID = e.EID
        WHERE e.AgeRestriction > ageRestrictionLimit
        AND ft.TicketID = t.TicketID
    );
END@
