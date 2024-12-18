/*
 * MIT License
 *
 * Copyright (c) 2023-2024 Fabio Lima
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

/**
 * Returns a time-ordered UUID with Gregorian Epoch (UUIDv6).
 *
 * Referencie: https://www.rfc-editor.org/rfc/rfc9562.html
 *
 * MIT License.
 *
 */
create or replace function uuid6() returns uuid as $$
declare
begin
return uuid6(clock_timestamp());
end $$ language plpgsql;

create or replace function uuid6(p_timestamp timestamp with time zone) returns uuid as $$
declare

v_time double precision := null;

	v_gregorian_t bigint := null;
	v_clock_sequence_and_node bigint := null;

	v_gregorian_t_hex_a varchar := null;
	v_gregorian_t_hex_b varchar := null;
	v_clock_sequence_and_node_hex varchar := null;

	c_epoch double precision := 12219292800; -- RFC-9562 epoch: 1582-10-15
	c_100ns_factor double precision := 10^7; -- RFC-9562 precision: 100 ns

	c_version bigint := x'0000000000006000'::bigint; -- RFC-9562 version: b'0110...'
	c_variant bigint := x'8000000000000000'::bigint; -- RFC-9562 variant: b'10xx...'

begin

	v_time := extract(epoch from p_timestamp);

	v_gregorian_t := trunc((v_time + c_epoch) * c_100ns_factor);
	v_clock_sequence_and_node := trunc(random() * 2^30)::bigint << 32 | trunc(random() * 2^32)::bigint;

	v_gregorian_t_hex_a := lpad(to_hex((v_gregorian_t >> 12)), 12, '0');
	v_gregorian_t_hex_b := lpad(to_hex((v_gregorian_t & 4095) | c_version), 4, '0');
	v_clock_sequence_and_node_hex := lpad(to_hex(v_clock_sequence_and_node | c_variant), 16, '0');

return (v_gregorian_t_hex_a || v_gregorian_t_hex_b  || v_clock_sequence_and_node_hex)::uuid;

end $$ language plpgsql;

-------------------------------------------------------------------
-- EXAMPLE:
-------------------------------------------------------------------
--
-- select uuid6() uuid, clock_timestamp()-statement_timestamp() time_taken;
--
-- |uuid                                  |time_taken        |
-- |--------------------------------------|------------------|
-- |1eeca632-cf2a-65e0-85f3-151064c2409d  |00:00:00.000108   |
--

-------------------------------------------------------------------
-- EXAMPLE: generate a list
-------------------------------------------------------------------
--
-- with x as (select clock_timestamp() as t from generate_series(1, 1000))
-- select uuid6(x.t) uuid, x.t::text ts from x;
--
-- |uuid                                |ts                           |
-- |------------------------------------|-----------------------------|
-- |1eeca634-f783-63f0-9988-48906d79f782|2024-02-13 08:30:37.891480-03|
-- |1eeca634-f783-6c24-97af-605238f4c3d0|2024-02-13 08:30:37.891691-03|
-- |1eeca634-f783-6e7c-9c2e-624f24b87738|2024-02-13 08:30:37.891754-03|
-- |1eeca634-f784-6070-a67b-4fc6659143e7|2024-02-13 08:30:37.891800-03|
-- |1eeca634-f784-6200-befd-0e20be5b0087|2024-02-13 08:30:37.891842-03|
-- |1eeca634-f784-6390-8f79-d4dacec1c3e0|2024-02-13 08:30:37.891881-03|
-- |1eeca634-f784-6520-8ee7-96091b017d4c|2024-02-13 08:30:37.891920-03|
-- |1eeca634-f784-66b0-a63e-c285d8a63e21|2024-02-13 08:30:37.891958-03|
-- |1eeca634-f784-6840-8c00-38659c4bf807|2024-02-13 08:30:37.891997-03|
-- |1eeca634-f784-69d0-b775-4bbfd45eb99e|2024-02-13 08:30:37.892036-03|
--

-------------------------------------------------------------------
-- FOR TEST: the expected result is an empty result set
-------------------------------------------------------------------
--
-- with t as (select uuid6() as id from generate_series(1, 1000))
-- select * from t where (id is null or id::text !~ '^[a-f0-9]{8}-[a-f0-9]{4}-6[a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}$');
--