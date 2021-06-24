salesorderID = salesorder.get("salesorder_id");
salesorderdate = salesorder.get("date").toDate();
organizationID = organization.get("organization_id");
details = invokeurl
[
	url :"https://inventory.zoho.com.au/api/v1/salesorders/" + salesorderID + "?organization_id=" + organizationID
	type :GET
	connection:"zom"
];
so = details.get("salesorder");
cf = so.get("custom_fields");
for each  i in cf
{
	if(i.get("placeholder") == "cf_cogs2")
	{
		cogs = i.get("value");
	}
}
info cogs;
total = so.get("total");
info total;
profit1 = total - cogs;
profit = profit1.toDecimal();
info profit;
cfl = list();
cmap = Map();
cmap.put("label","Profit");//label
cmap.put("value",profit);
cfl.add(cmap);
json = Map();
json.put("custom_fields",cfl);
json.put("salesorder_id",so.get("salesorder_id"));
params = Map();
params.put("JSONString",json);
info json;
response = invokeurl
[
	url :"https://inventory.zoho.com.au/api/v1/salesorders/" + salesorderID + "?organization_id=" + organizationID
	type :PUT
	parameters:params
	connection:"zom"
];
info response.get("message");
